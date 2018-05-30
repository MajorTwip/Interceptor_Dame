package ch.ffhs.ftoop.interceptor.dame.beans;

import org.apache.commons.math3.exception.OutOfRangeException;

import java.util.Objects;
import java.util.Optional;

/**
 * Coordinates on the Gameboard
 *
 * @author MajorTwip
 */
public class Coordinate {
    private int x;
    private int y;


    /**
     * Instanciates Coordinate
     *
     * @param x Horizontal coordinate, from left to right
     * @param y Vertical coordinate, from bottom to top
     */
    public Coordinate(int x, int y) throws OutOfRangeException {
        if (x < 0) throw new OutOfRangeException(x, 0, Integer.MAX_VALUE);
        if (y < 0) throw new OutOfRangeException(y, 0, Integer.MAX_VALUE);
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
                    coordinate.getX() + direction.getOffsetX() * factor,
                    coordinate.getY() + direction.getOffsetY() * factor
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
     * Get the coordinate between (half way) two coordinates
     * if the distance between the two coordinates is odd-numbered the function returns an Optional.empty
     *
     * @param coordinate1 First Coordinate
     * @param coordinate2 Second Coordinate
     * @return Optional coordinate between the two arguments, if the distance is not odd-numbered
     */
    public static Optional<Coordinate> getCoordinateBetween(Coordinate coordinate1, Coordinate coordinate2) {
        int offsetX = coordinate2.getX() - coordinate1.getX();
        int offsetY = coordinate2.getY() - coordinate1.getY();
        if (offsetX % 2 != 0 || offsetY % 2 != 0) return Optional.empty();

        return Optional.of(new Coordinate(coordinate1.getX() + offsetX / 2, coordinate1.getY() + offsetY / 2));
    }

    /**
     * Common equals-method
     *
     * @param obj Coordinate to compare
     * @return true if equal values
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;

        Coordinate coordinate = (Coordinate) obj;
        return this.x == coordinate.getX() && this.y == coordinate.getY();
    }

    /**
     * Common hashCode-method
     *
     * @return true if equal values
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return getX() + "/" + getY();
    }
}