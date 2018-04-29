package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.*;

import java.util.ArrayList;

interface TurnCheck {
    Boolean check(Stone stone, Coordinate coordinate);
}

/**
 * Backend to the Dame-Game
 *
 * @author simcrack
 */
public class Backend implements DameBackendInterface {
    GUI gui;
    Board actualBoard;
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
            return !actualBoard.getStoneAt(c).getIsOwn() && s.getIsOwn();
        } catch (RuntimeException e) {
            return false;
        }
    };
    private TurnCheck isTurnBlockedByMe = (Stone s, Coordinate c) -> {
        try {
            return actualBoard.getStoneAt(c).getIsOwn() && !s.getIsOwn();
        } catch (RuntimeException e) {
            return false;
        }
    };

    public Backend() {
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void startNewGame(GameMode mode) {
        actualBoard = new Board(7, 7);
        actualBoard.addStone(new Stone(new Coordinate(2, 4), true, false));
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
    @Override
    public boolean getTurnIsLegal(Stone stone, Coordinate coordinate) {
        return false;

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
        // TODO Auto-generated method stub
        return false;
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

    public ArrayList<Coordinate> getAllowedCoordinates(Stone stone) {
        ArrayList<Coordinate> allowedCoordinates = new ArrayList<>();
        for (Direction direction : Direction.getAllDirections()) {
            Coordinate.createCoordinate(stone.getCoordinate(), direction, 1)
                    .ifPresent(allowedCoordinates::add);
        }
        for (Coordinate c : allowedCoordinates) {
            if (!isTurnDirAllowed.check(stone, c)) {
                allowedCoordinates.remove(c);
                continue;
            }
            if (isTurnBlockedByMe.check(stone, c)) {
                allowedCoordinates.remove(c);
                continue;
            }
            if (isTurnBlockedByEnemy.check(stone, c)) {
                allowedCoordinates.remove(c);
                //TODO: add coordinate behind this coordinate
            }
        }

        return allowedCoordinates;
    }

}
