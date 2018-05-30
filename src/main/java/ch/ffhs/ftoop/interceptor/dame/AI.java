package ch.ffhs.ftoop.interceptor.dame;
/**
 * Static Methods to simulate enemy
 *
 * @author MajorTwip
 * @version 0.1
 */

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Artificial Intelligence... In fact just a mechanism which chooses a possible turn, if possible the one killing the most enemies
 *
 * @author MajorTwip
 * @version 0.1
 */
public class AI {

    /**
     * function which is called whenever the AI have to apply a turn
     *
     * @param backend a reference to a Backend instance for which the turn must be applied
     */
    public static void makeTurn(Backend backend) {
        Board actualBoard = backend.getActualBoard();
        Board newBoard = copyBoard(actualBoard);


        System.out.println();
        System.out.println("new turn for enemy initiated");
        //System.out.println(actualBoard);
        //System.out.println(newBoard);
        LinkedList<Coordinate> turns = getEnemyTurns(newBoard);
        if (turns.size() < 2) {
            System.out.println("No enemy turns possible");
        } else {
            System.out.println();
            System.out.println("Applying");
            turns.stream().forEach(System.out::println);
            Iterator<Coordinate> turn = turns.iterator();
            Stone stoneToMove = actualBoard.getStoneAt(turn.next());
            Coordinate newCoord = turn.next();
            backend.applyTurn(stoneToMove, newCoord);
            stoneToMove = actualBoard.getStoneAt(newCoord);

            while (turn.hasNext()) {
                newCoord = turn.next();
                if (RuleCheck.canKillWithTurn(actualBoard, stoneToMove, newCoord)) {
                    backend.applyTurn(stoneToMove, newCoord);
                    stoneToMove = actualBoard.getStoneAt(newCoord);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Returns a copy of a given Board.
     *
     * @param oldBoard The Board which acts as sample
     * @return Board which is a copy of the given one
     */
    private static Board copyBoard(Board oldBoard) {
        Board newBoard = new Board(oldBoard.getMaxX(), oldBoard.getMaxY());
        for (Stone stone : oldBoard) {
            newBoard.add(new Stone(stone.getCoordinate(), stone.getIsOwn(), stone.getIsQueen()));
            newBoard.setOwnTurn(false);
        }
        return newBoard;
    }

    /**
     * A mechanism which chooses a possible turn, if possible the one killing the most enemies
     *
     * @param board the actual board, on iterations already the future board
     * @return A list of coordinates. 0 when no Stone left, 1 when stones but no possible moves, and 2or more when turns possible. First coordinate is the moving stone.
     * TODO MajorTwip: rewrite, with functions of the RuleCheck Class
     */
    private static LinkedList<Coordinate> getEnemyTurns(Board board) {
        System.out.println("New Iteration with Board:");
        System.out.println(board.toString());
        LinkedList<Coordinate> turns = new LinkedList<>();
        boolean enemyKillable = RuleCheck.canKillEnemy(board);
        for (Stone stone : board) {
            if (!stone.getIsOwn()
                    && !(enemyKillable && !RuleCheck.canKillEnemy(board, stone))) {
                turns = getTurnsForStone(board, turns, stone);
            }
        }
        turns.stream().forEach(System.out::println);
        return turns;
    }

    private static LinkedList<Coordinate> getTurnsForStone(Board board, LinkedList<Coordinate> turns, Stone stone) {
        {
            LinkedList<Coordinate> possibleTurns = new LinkedList<Coordinate>();
            RuleCheck.getAllowedCoordinates(board, stone, possibleTurns); //found coordinates are stored in possibleTurns
            System.out.print(stone.toString() + " : ");
            System.out.println(String.valueOf(possibleTurns.size()) + ": " + possibleTurns.stream().map(n -> n.toString()).collect(Collectors.joining(";")));
            if (possibleTurns.size() > 0) {
                //if there is at least one possible turn, the initial turn (coordinates of the stone) is added
                // if not the turns array shall be rest free
                if (turns.size() == 0) {
                    turns.add(stone.getCoordinate());
                }

                for (Coordinate turn : possibleTurns) {
                    if (RuleCheck.canKillWithTurn(board, stone, turn)) {
                        Board boardForRecursion = copyBoard(board);

                        //because board and its stone was copied, the stone must newly discovered with getStoneAt
                        turns.addAll(getKillEscalationCoordinates(boardForRecursion, boardForRecursion.getStoneAt(stone.getCoordinate()), turn));
                    } else {
                        if (turns.size() < 2) {
                            turns.add(turn);
                        }
                    }

                }
            }
        }
        return turns;
    }

    /**
     * creates a chain of turn
     * is usually called after the AI wants to kill a stone to detect the kill-path through the opposite stones
     *
     * @param board a copy of the board (this function make moves on it!)
     * @param stone the stone which can kill an opposite stone
     * @param turn  the turn which the stone must do for killing the opposite stone
     * @return a LinkedList with a path of Coordinates (e.g. {1/3; 3/5; 1/7})
     */
    private static LinkedList<Coordinate> getKillEscalationCoordinates(Board board, Stone stone, Coordinate turn) {
        LinkedList<Coordinate> turns = new LinkedList<>();

        turns.add(turn);

        //make move on recursive board
        Coordinate.getCoordinateBetween(stone.getCoordinate(), turn).ifPresent(board::removeStoneAt);
        stone.setCoordinate(turn);
        if (stone.getCoordinate().getY() == board.getMaxY()) {
            stone.setIsQueen(true);
        }

        //if on the new position the stone can kill another stone make a recursive call an so create a change of turns
        List<Coordinate> newKillCoordinates = new ArrayList<>();
        if (RuleCheck.getAllowedCoordinates(board, stone, newKillCoordinates)) { //true->killCoordinates
            turns.addAll(getKillEscalationCoordinates(board, stone, newKillCoordinates.get(0)));
        }

        return turns;
    }
}
