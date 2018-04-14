package ch.ffhs.ftoop.interceptor.dame.beans;

import java.util.LinkedList;

import org.apache.commons.math3.exception.OutOfRangeException;

/**
 * Represents a Gameboard
 * @author MajorTwip
 *
 */
public class Board extends LinkedList<Stone>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int maxX;
	int maxY;
	
	/**
	 * Instanciates an empty Gameboard
	 * 
	 * @param maxX Horizontal dimension
	 * @param maxY Vertical dimension
	 * @throws OutOfRangeException 
	 */
	Board(int maxX, int maxY) throws OutOfRangeException{
		super();
		if(maxX<4) throw new OutOfRangeException(maxX, 4, 255);
		if(maxY<4) throw new OutOfRangeException(maxY, 4, 255);
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public void addStone(Stone stone) throws RuntimeException{
		if(fieldFree(stone.getCoordinate())) {
			this.add(stone);
		}
	}

	private boolean fieldFree(Coordinate coordinate) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
