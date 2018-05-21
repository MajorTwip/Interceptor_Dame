package ch.ffhs.ftoop.interceptor.dame.beans;

/**
 * Helperclass that checks if turns are legal and applies them
 * @author MajorTwip
 *
 */
public class TurnApplier {
	
	/**
	 * 
	 * @param board on which the turn has to be checked
	 * @param stone to move
	 * @param coord where to move to
	 * @return is the intended move legal
	 */
	public static Boolean isLegal(Board board, Stone stone, Coordinate coord) {
		return false;

	}
	
	/**
	 * 
	 * @param board on which the turn has to be checked
	 * @param stone to move
	 * @param coord where to move to
	 * @return true if an other stone is killed
	 */
	public static Boolean isKilling(Board board, Stone stone, Coordinate coord) {
		return false;

	}
	
	
	public static Board applyTurn(Board board, Stone stone, Coordinate coord) {
		Board newBoard = new Board(board.getMaxX(), board.getMaxY());
		for(Stone oldstone:board) {
			newBoard.addStone(new Stone(oldstone.getCoordinate(),oldstone.getIsOwn(),oldstone.getIsQueen()));
		}
		
		return newBoard;
	}
	
}
