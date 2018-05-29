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

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Artificial Intelligence... In fact just a mechanism which chooses a possible turn, if possible the one killing the most enemies
 *
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
		
		System.out.println(actualBoard);
		System.out.println(newBoard);
		LinkedList<Coordinate> turns = getEnemyTurns(newBoard);
		if(turns.size()<2) {
			System.out.println("No enemy turns possible");
		}else {
			Iterator<Coordinate> turn = turns.iterator();
			Stone stoneToMove = actualBoard.getStoneAt(turn.next());
			while(turn.hasNext()) {
				Coordinate newCoord = turn.next();
				backend.applyTurn(stoneToMove, newCoord);
				stoneToMove = actualBoard.getStoneAt(newCoord);
			}
		}
	}
	
	/**
	 * Returns a copy of a given Board. 
	 * @param oldBoard The Board which acts as sample
	 * @return Board which is a copy of the given one
	 */
	private static Board copyBoard(Board oldBoard) {
		Board newBoard = new Board(oldBoard.getMaxX(),oldBoard.getMaxY());
		for(Stone stone:oldBoard) {
			newBoard.add(new Stone(stone.getCoordinate(),stone.getIsOwn(), stone.getIsQueen()));
		}
		return newBoard;
	}

	/**
	 * A mechanism which chooses a possible turn, if possible the one killing the most enemies
	 * @param board the actual board, on iterations already the future board
	 * @return A list of coordinates. 0 when no Stone left, 1 when stones but no possible moves, and 2or more when turns possible. First coordinate is the moving stone.
	 * TODO MajorTwip: rewrite, with functions of the RuleCheck Class
	 */
	private static LinkedList<Coordinate> getEnemyTurns(Board board) {
		LinkedList<Coordinate> turns = new LinkedList<>();
		for (Stone stone : board) {
			if (!stone.getIsOwn()) {
				if (turns.size() == 0) {
					turns.add(stone.getCoordinate());
				}
				LinkedList<Coordinate> possibleTurns = new LinkedList<Coordinate>();
				RuleCheck.getAllowedCoordinates(board, stone, possibleTurns); //found coordinates are stored in possibleTurns
				System.out.print(stone.toString() + " : ");
				System.out.println(String.valueOf(possibleTurns.size()));
				if (possibleTurns.size() > 0) {
					for (Coordinate turn : possibleTurns) {
						if (RuleCheck.canKillWithTurn(board, stone, turn)) {
							Board boardForRecursion = copyBoard(board);
							boardForRecursion.getStoneAt(stone.getCoordinate()).setCoordinate(turn);
							LinkedList<Coordinate> futureTurns = getEnemyTurns(boardForRecursion);
							if (futureTurns.size() > (turns.size() - 1)) {
								turns = futureTurns;
								turns.addFirst(stone.getCoordinate());
							}
						} else {
							if (turns.size() < 2) {
								turns.add(turn);
							}
						}

					}
				}
			}
		}

		return turns;
	}
}
