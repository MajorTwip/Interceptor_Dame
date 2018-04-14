package ch.ffhs.ftoop.interceptor.dame.beans;

import org.apache.commons.math3.exception.OutOfRangeException;

/**
 * Coordinates on the Gameboard
 * 
 * @author MajorTwip
 *
 */
public class Coordinate {
	int x;
	int y;
	
	
	/**
	 * Instanciates Coordinate
	 * 
	 * @param x Horizontal coordinate, from left to right
	 * @param y Vertical coordinate, from bottom to top
	 */
	public Coordinate(int x, int y) throws OutOfRangeException {
		if(x<0) throw new OutOfRangeException(x, 1, 10);
		if(y<0) throw new OutOfRangeException(y, 1, 10);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter Horizontal coordinate, from left to right
	 * @return Horizontal coordinate, from left to right
	 */
	public int getX() {
		return x;
	}
	
	
	/**
	 * Getter Vertical coordinate, from bottom to top
	 * @return Vertical coordinate, from bottom to top
	 */
	public int getY() {
		return y;
	}
	
	
	/**
	 * Set Coordinate
	 * 
	 * @param x Horizontal coordinate, from left to right
	 * @param y Vertical coordinate, from bottom to top
	 */
	public void setXY(int x, int y) throws OutOfRangeException {
		if(x<0) throw new OutOfRangeException(x, 1, 10);
		if(y<0) throw new OutOfRangeException(y, 1, 10);
		this.x = x;
		this.y = y;
	}
}
