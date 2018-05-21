package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

interface TurnCheck {
    Boolean check(Stone stone, Coordinate coordinate);
}

/**
 * Backend to the Dame-Game
 *
 * @author simcrack
 */
public class Backend implements DameBackendInterface {
    private GUI gui;
    private Board actualBoard;
    private boolean ownTurn = true; //if true it is the move of the player for which stone.isOwn() = true
    private TurnCheck isTurnForward = (Stone s, Coordinate c) -> s.getCoordinate().getY() > c.getY();
    private TurnCheck isTurnBackward = (Stone s, Coordinate c) -> s.getCoordinate().getY() < c.getY();
    private TurnCheck isTurnDirAllowed = (Stone s, Coordinate c) -> {
        if (s.getIsQueen()) {
            return isTurnForward.check(s, c) || isTurnBackward.check(s, c);
        }
        if (s.getIsOwn()) {
            return isTurnForward.check(s, c);
        } else {
            return isTurnBackward.check(s, c);
        }
    };
    private TurnCheck isTurnOnBoard = (Stone s, Coordinate c) -> c.getX() >= 0 &&
            c.getY() >= 0 &&
            c.getX() <= actualBoard.getMaxX() &&
            c.getY() <= actualBoard.getMaxY();
    private TurnCheck isTurnBlockedByEnemy = (Stone s, Coordinate c) -> {
        try {
            return actualBoard.getStoneAt(c).getIsOwn() != s.getIsOwn();
        } catch (RuntimeException e) {
            return false;
        }
    };
    private TurnCheck isTurnBlockedByMe = (Stone s, Coordinate c) -> {
        try {
            return actualBoard.getStoneAt(c).getIsOwn() == s.getIsOwn();
        } catch (RuntimeException e) {
            return false;
        }
    };


    boolean stoneCanBeSelected(Stone stone) {
        return stone.getIsOwn() == ownTurn;
    }

    void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void startNewGame(GameMode mode) {
        actualBoard = new Board(7, 7);
        int coordinates[][] = {{0, 0}, {2, 0}, {4, 0}, {6, 0}, {1, 1}, {3, 1}, {5, 1}, {7, 1}};
        for (int coordinate[] : coordinates) {
            actualBoard.addStone(new Stone(new Coordinate(coordinate[0], coordinate[1]), false, false));
            actualBoard.addStone(new Stone(new Coordinate(coordinate[0], actualBoard.getMaxY() - 1 + coordinate[1]), true, false));
        }
        gui.showBoard(actualBoard);
    }

    /**
     * Exits the game. Yields eventually an Alert in am MessageBox
     */
    @Override
    public void quitGame() {
        System.exit(0);
    }

    /**
     * Checks if it would be legal to move stone Stone to a new Coodinate
     *
     * @param stone      Stone to move
     * @param coordinate Coordinate to move to
     * @return Boolean, true if move would be legal
     */
    public boolean getTurnIsLegal(Stone stone, Coordinate coordinate) {
        ArrayList<Coordinate> allowedCoordinates = new ArrayList<>();
        //if this stone can not be used to kill an enemy stone but there is an other stone which can do this,
        // the turn with tis stone is not allowed
        if (!getAllowedCoordinates(stone, allowedCoordinates)
                && canKillEnemy()) {
            return false;
        }
        return allowedCoordinates.contains(coordinate);
    }

    /**
     * applies a move, move stone Stone to a new Coodinate
     *
     * @param stone      Stone to move
     * @param coordinate Coordinate to move to
     * @return Boolean, true if move was successful
     */
    @Override
    public boolean applyTurn(Stone stone, Coordinate coordinate) {
        gui.animateMove(stone, coordinate);
    	
        Coordinate.getCoordinateBetween(stone.getCoordinate(), coordinate).ifPresent(actualBoard::removeStoneAt);
        stone.setCoordinate(coordinate);

        //if stone reached the end of the board (on the opposite) it becomes a queen
        if (coordinate.getY() == actualBoard.getMaxY() && !stone.getIsOwn() ||
                coordinate.getY() == 0 && stone.getIsOwn()) {
            stone.setIsQueen(true);
        }
        gui.redraw(); 
        ownTurn = !ownTurn;
        getVictory().ifPresent((Boolean isOwn) -> {
            if (isOwn) {
                gui.showMessageBox(new MessageBox(MessageBox.MsgType.VICTORY));
            } else {
                gui.showMessageBox(new MessageBox(MessageBox.MsgType.LOSE));
            }
        });
        return true;
    }

    /**
     * Returns Board with the actual state of the game
     *
     * @return Board with actual state
     */
    @Override
    public Board getActualBoard() {
        return actualBoard;
    }

    /**
     * Gets all allowed new coordinates for a given stone
     *
     * @param stone       stone for which the allowed coordinates shall be returned
     * @param coordinates Arraylist of coordinates ByRef in which allowed coordinates are written
     * @return true if an enemy can be killed with this stone, false if not
     */
    private boolean getAllowedCoordinates(Stone stone, ArrayList<Coordinate> coordinates) {
        ArrayList<Coordinate> moveCoordinates = new ArrayList<>();
        ArrayList<Coordinate> killCoordinates = new ArrayList<>();

        for (Direction direction : Direction.getAllDirections()) {
            Coordinate.createCoordinate(stone.getCoordinate(), direction, 1)
                    .ifPresent(moveCoordinates::add);
        }

        Iterator<Coordinate> itr = moveCoordinates.iterator();
        while (itr.hasNext()) {
            Coordinate coordinate = itr.next();
            if (!isTurnOnBoard.check(stone, coordinate)
                    || !isTurnDirAllowed.check(stone, coordinate)
                    || isTurnBlockedByMe.check(stone, coordinate)) {
                itr.remove();
                continue;
            }
            if (isTurnBlockedByEnemy.check(stone, coordinate)) {
                Direction direction = Direction.getDirection(stone.getCoordinate(), coordinate).orElse(new Direction(0, 0, "Null"));
                Coordinate.createCoordinate(stone.getCoordinate(), direction, 2)
                        .ifPresent((Coordinate c) -> {
                            if (actualBoard.fieldFree(c) && isTurnOnBoard.check(stone, c)) {
                                killCoordinates.add(c);
                            }
                        });
            }
        }

        if (killCoordinates.isEmpty()) {
            coordinates.addAll(moveCoordinates);
            return false;
        } else {
            coordinates.addAll(killCoordinates);
            return true;
        }

    }

    /**
     * Checks if there is a Stone which can kill an enemy (if so it have to be moved with it)
     *
     * @return true if there is a Stone which can kill an enemy, false if not
     */
    private boolean canKillEnemy() {
        ArrayList<Coordinate> dummyArray = new ArrayList<>(); //unnecessary but a must have for getAllowedCoordinates
        for (Stone stone : actualBoard) {
            if (stone.getIsOwn() == ownTurn && getAllowedCoordinates(stone, dummyArray)) return true;
        }
        return false;
    }

    /**
     * Checks if somebody has won and if so, who
     *
     * @return Optional Boolean (true: Player 1 has won, false: Player 2 / Computer has won, Optional.empty: nobody has won
     */
    private Optional<Boolean> getVictory() {
        if (actualBoard.stream().map(Stone::getIsOwn).distinct().count() == 1) {
            return Optional.of(actualBoard.getFirst().getIsOwn());
        }
        return Optional.empty();
    }
}
