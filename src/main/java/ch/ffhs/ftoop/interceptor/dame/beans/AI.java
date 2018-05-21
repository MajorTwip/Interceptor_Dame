package ch.ffhs.ftoop.interceptor.dame.beans;
/**
 * Static Methods to simulate enemy
 * 
 * @author MajorTwip
 * @version 0.1
 */

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
	 * A mechanism which chooses a possible turn, if possible the one killing the most enemies
	 * @param board the actual board, on iterations already the future board
	 * @return A list of coordinates. 0 when no Stone left, 1 when stones but no possible moves, and 2or more when turns possible. First coordinate is the moving stone.
	 */
	public static LinkedList<Coordinate> getEnemyTurns(Board board){		
		LinkedList<Coordinate> turns = new LinkedList<>();
		for(Stone stone:board) {
			if(!stone.getIsOwn()) {
				if(turns.size()==0) {
					turns.add(stone.getCoordinate());
				}
				LinkedList<Coordinate> possibleTurns = checkForMoves(board, stone);
				if(possibleTurns.size()>0) {
					for(Coordinate turn:possibleTurns) {
						if(TurnApplier.isKilling(board, stone, turn)) {
							LinkedList<Coordinate> futureTurns = getEnemyTurns(TurnApplier.applyTurn(board, stone, turn));
							if(futureTurns.size()>(turns.size() -1)) {
								turns = futureTurns;
								turns.addFirst(stone.getCoordinate());
							}
						}else {
							if(turns.size()<2) {
								turns.add(turn);
							}
						}
						
					}
				}					
			}
		}
		
		return turns;
	}
	
	/**
	 * Returns all legal moves for a given stone
	 * @param board on which to play
	 * @param stone to move
	 * @return List of all possible destinantions
	 */
	private static LinkedList<Coordinate> checkForMoves(Board board,Stone stone){
		LinkedList<Coordinate> possibleTurns = new LinkedList<>();
		for(int x = 0;x<=board.getMaxX();x++) {
			for(int y = 0;y<=board.getMaxY();y++) {
				Coordinate coord = new Coordinate(x, y);
				if(TurnApplier.isLegal(board, stone, coord)) {
					possibleTurns.add(coord);
				}
			}
		}
		return possibleTurns;
	}

}
