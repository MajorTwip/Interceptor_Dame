package ch.ffhs.ftoop.interceptor.dame.beans;

import org.apache.commons.math3.exception.OutOfRangeException;

import java.util.Optional;

/**
 * Coordinates on the Gameboard
 *
 * @author MajorTwip
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
        if (x < 0) throw new OutOfRangeException(x, 1, 10);
        if (y < 0) throw new OutOfRangeException(y, 1, 10);
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a new Coordinate Object and returns in an Optional
     * Does not throw Exception but returns an Optional.empty if the instantiation fails
     *
     * @param x Horizontal coordinate, from left to right
     * @param y Vertical coordinate, from bottom to top
     * @return Coordinate Object wrapped in an Optional
     */
    public static Optional<Coordinate> createCoordinate(int x, int y) {
        try {
            return Optional.of(new Coordinate(x, y));
        } catch (OutOfRangeException e) {
            return Optional.empty();
        }
    }

    /**
     * Instantiates a new Coordinate Object and returns in an Optional
     * Does not throw Exception but returns an Optional.empty if the instantiation fails
     * Does the same as createCoordinate(int x, int y) but adds the offset of the param d and a factor
     *
     * @param coordinate coordinate object of the current position
     * @param direction  offset direction at which the new coordinate shall be created
     * @param factor     factor which shall be multiplied whith the offset
     * @return Coordinate object wrapped in an Optional
     */
    public static Optional<Coordinate> createCoordinate(Coordinate coordinate, Direction direction, int factor) {
        try {
            return Optional.of(new Coordinate(
                    coordinate.getX() + direction.offsetX * factor,
                    coordinate.getY() + direction.offsetY * factor
            ));
        } catch (OutOfRangeException e) {
            return Optional.empty();
        }
    }

    /**
     * Getter Horizontal coordinate, from left to right
     *
     * @return Horizontal coordinate, from left to right
     */
    public int getX() {
        return x;
    }

    /**
     * Getter Vertical coordinate, from bottom to top
     *
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
        if (x < 0) throw new OutOfRangeException(x, 1, 10);
        if (y < 0) throw new OutOfRangeException(y, 1, 10);
        this.x = x;
        this.y = y;
    }

    /**
     * Common equals-method
     *
     * @param coordinate Coordinate to compare
     * @return true if equal values
     */
    public Boolean equals(Coordinate coordinate) {
        if ((this.x == coordinate.getX()) && (this.y == coordinate.getY())) {
            return true;
        } else {
            return false;
        }
    }
}
