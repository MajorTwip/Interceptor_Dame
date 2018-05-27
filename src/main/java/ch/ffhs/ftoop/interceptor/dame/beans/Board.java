package ch.ffhs.ftoop.interceptor.dame.beans;

import org.apache.commons.math3.exception.OutOfRangeException;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Represents a Gameboard
 *
 * @author MajorTwip
 */
public class Board extends LinkedList<Stone> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private boolean ownTurn = true; //if true it is the move of the player for which stone.isOwn() = true
	private Optional<Stone> stoneEnforcement = Optional.empty(); //if this variable is set, only the given stone can be selected
	private int maxX;
	private int maxY;

	/**
	 * Instanciates an empty Gameboard
	 *
	 * @param maxX Horizontal dimension
	 * @param maxY Vertical dimension
	 * @throws OutOfRangeException if Board should be too small
	 */
	public Board(int maxX, int maxY) throws OutOfRangeException {
		super();
		if (maxX < 4) throw new OutOfRangeException(maxX, 3, 255);
		if (maxY < 4) throw new OutOfRangeException(maxY, 3, 255);
		this.maxX = maxX;
		this.maxY = maxY;
	}

	/**
	 * Adds a given Stone to Board
	 *
	 * @param stone Stone to add
	 * @throws RuntimeException    If field is already occupied
	 * @throws OutOfRangeException If stone's coordinates exceed the size of the board
	 */
	public void addStone(Stone stone) throws RuntimeException, OutOfRangeException {
		if (stone.getCoordinate().getX() > maxX) throw new OutOfRangeException(stone.getCoordinate().getX(), 0, maxX);
		if (stone.getCoordinate().getY() > maxY) throw new OutOfRangeException(stone.getCoordinate().getY(), 0, maxY);
		if (fieldFree(stone.getCoordinate())) {
			this.add(stone);
		} else {
			throw new RuntimeException("Filed already occupied");
		}
	}

	public void removeStoneAt(Coordinate coordinate) {
		try {
			this.remove(getStoneAt(coordinate));
		} catch (RuntimeException e) {
			System.out.println("Stone at " + coordinate + " was not found and so could not be removed");
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the given field is free
	 *
	 * @param coordinate Coordinate to check
	 * @return True if field is free
	 */
	public boolean fieldFree(Coordinate coordinate) {
		try {
			getStoneAt(coordinate);
		} catch (RuntimeException e) {
			return true;
		}
		return false;
	}

	/**
	 * Return Stone at given coordinates
	 *
	 * @param coordinate of the stone
	 * @return Requested stone
	 * @throws RuntimeException if no stone is at given coordinates
	 */
	public Stone getStoneAt(Coordinate coordinate) throws RuntimeException {
		for (Stone stone : this) {
			if (stone.getCoordinate().equals(coordinate)) {
				return stone;
			}
		}
		throw new RuntimeException("No Stone at this coordinate");
	}

	/**
	 * Return horizontal limit
	 *
	 * @return maxvalue of X
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * Return vertical limit
	 *
	 * @return maxvalue of Y
	 */
	public int getMaxY() {
		return maxY;
	}

	public boolean isOwnTurn() {
		return ownTurn;
	}

	public void setOwnTurn(boolean ownTurn) {
		this.ownTurn = ownTurn;
	}

	public Optional<Stone> getStoneEnforcement() {
		return stoneEnforcement;
	}

	public void setStoneEnforcement(Optional<Stone> stoneEnforcement) {
		this.stoneEnforcement = stoneEnforcement;
	}
}
