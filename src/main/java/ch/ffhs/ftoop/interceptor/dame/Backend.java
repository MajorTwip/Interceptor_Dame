package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.*;

import java.util.Optional;

/**
 * Backend of the Dame-Game
 *
 * @author simcrack
 */
public class Backend implements DameBackendInterface {
    private GUI gui;
    private Board actualBoard;
    private boolean singlePlayer;

    void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void startNewGame(GameMode mode) {
        singlePlayer = mode.isSinglePlayer();

        actualBoard = new Board(7, 7);
        for (int coordinate[] : mode.getCoordinates()) {
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

    @Override
    public boolean getTurnIsLegal(Stone stone, Coordinate coordinate) {
        return RuleCheck.getTurnIsLegal(actualBoard, stone, coordinate);
    }

    /**
     * checks if a stone can be selected (is only possible if it is the turn of the stone owner)
     *
     * @param stone the stone for which shall be tested
     * @return true if the stone belongs to the current player (can be selected), false if not
     */
    boolean stoneCanBeSelected(Stone stone) {
        //in singleplayer enemy stones cant never be selected
        if (isSinglePlayer() && !actualBoard.isOwnTurn()) {
            return false;
        }
        return RuleCheck.stoneCanBeSelected(actualBoard, stone);
    }

    /**
     * applies a move, move stone Stone to a new Coordinate
     *
     * @param stone      Stone to move
     * @param coordinate Coordinate to move to
     * @return Boolean, true if move was successful
     */
    @Override
    public boolean applyTurn(Stone stone, Coordinate coordinate) {
		if (!RuleCheck.getTurnIsLegal(actualBoard, stone, coordinate)) {
		    throw(new IllegalArgumentException("Trun with " + stone + " to " + coordinate + " is against the rules"));
        }

        boolean killedStone = false;
        gui.animateMove(stone, coordinate);

        //if the turn was enforced the stonEnforcment must be removed
        if (actualBoard.getStoneEnforcement().isPresent()) {
            actualBoard.setStoneEnforcement(Optional.empty());
        }

        //Check if a stone can be killed with this turn an execute it
        Optional<Coordinate> killCoordinate = Coordinate.getCoordinateBetween(stone.getCoordinate(), coordinate);
        if (killCoordinate.isPresent()) {
            actualBoard.removeStoneAt(killCoordinate.get());
            killedStone = true;
        }

        stone.setCoordinate(coordinate);

        //if stone reached the end of the board (on the opposite) it becomes a queen
        if (coordinate.getY() == actualBoard.getMaxY() && !stone.getIsOwn() ||
                coordinate.getY() == 0 && stone.getIsOwn()) {
            stone.setIsQueen(true);
        }
        gui.redraw();

        //check if somebody has won
        getVictory().ifPresent((Boolean isOwn) -> {
            if (isOwn) {
                gui.showMessageBox(new MessageBox(MessageBox.MsgType.VICTORY));
            } else {
                gui.showMessageBox(new MessageBox(MessageBox.MsgType.LOSE));
            }
        });

        //check if an additional turn is possible
        if (!RuleCheck.canKillEnemy(actualBoard, stone) || !killedStone) {
            actualBoard.setOwnTurn(!actualBoard.isOwnTurn()); //no additional turn
        } else {
            //additional turn but with same stone (is stated in the stoneEnforcement variable of the board)
            actualBoard.setStoneEnforcement(Optional.of(stone));
        }
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

    boolean isSinglePlayer() {
        return singlePlayer;
    }
}
