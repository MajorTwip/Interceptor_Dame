package ch.ffhs.ftoop.interceptor.dame.beans;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

/**
 * represents a direction of the field
 *
 * @author simcrack
 * @version 20180428
 */
public class Direction {
    private int offsetX;
    private int offsetY;
    private String description;

    /**
     * instantiates a new Direction object
     *
     * @param offsetX     the offset of the x coordinate
     * @param offsetY     the offset of the y coordinate
     * @param description the description of the direction
     */
    public Direction(int offsetX, int offsetY, String description) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.description = description;
    }

    /**
     * returns a list of all possible direction
     *
     * @return list of direction objects
     */
    public static ArrayList<Direction> getAllDirections() {
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(new Direction(-1, -1, "LeftDown"));
        directions.add(new Direction(-1, 1, "LeftUp"));
        directions.add(new Direction(1, -1, "RightDown"));
        directions.add(new Direction(1, 1, "RightUp"));
        return directions;
    }

    /**
     * Gets the direction from a start and and end coordinate
     * can only handle directions which have 45° (which are allowed by getAllDirections
     *
     * @param coordinate1 coordinate of the start
     * @param coordinate2 coordinate of the end
     * @return direction from coordinate1 to coordinate2
     */
    public static Optional<Direction> getDirection(Coordinate coordinate1, Coordinate coordinate2) {
        int offsetX = coordinate2.getX() - coordinate1.getX();
        int offsetY = coordinate2.getY() - coordinate1.getY();
        if (offsetX > 1 || offsetX < -1) {
            offsetY = offsetY / Math.abs(offsetX);
            offsetX = offsetX / Math.abs(offsetX);
        }
        for (Direction direction : Direction.getAllDirections()) {
            if (direction.getOffsetX() == offsetX && direction.getOffsetY() == offsetY) {
                return Optional.of(direction);
            }
        }
        return Optional.empty();
    }

    public Pair<Integer, Integer> getOffset() {
        return new Pair<>(this.offsetX, this.offsetY);
    }

    /**
     * Common equals-method
     *
     * @param obj Direction to compare
     * @return true if equal values
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Direction)) return false;

        Direction direction = (Direction) obj;
        return this.getOffsetX() == direction.getOffsetX() &&
                this.getOffsetY() == direction.getOffsetY();
    }

    /**
     * Overrides defaul hashCode (because equal is overritten)
     *
     * @return hash from offsetX and offsetY
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getOffsetX(), this.getOffsetY());
    }

    @Override
    public String toString() {
        return "Direction " + this.description + " Offset: " + this.offsetX + "/" + this.offsetY;
    }

    int getOffsetX() {
        return offsetX;
    }

    int getOffsetY() {
        return offsetY;
    }
}
