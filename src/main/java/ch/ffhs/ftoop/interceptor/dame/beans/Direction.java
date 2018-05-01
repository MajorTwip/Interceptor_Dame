package ch.ffhs.ftoop.interceptor.dame.beans;

import java.util.ArrayList;
import java.util.Optional;

/**
 * represents a direction of the field
 *
 * @author simcrack
 * @version 20180428
 */
public class Direction {
    int offsetX;
    int offsetY;
    public String description;

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
     * Gets the direction from a start and an end coordinate
     * can only handle directions which have 90° or 45°
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

    /**
     * Common equals-method
     *
     * @param direction Direction to compare
     * @return true if equal values
     */
    public Boolean equals(Direction direction) {
        return this.getOffsetX() == direction.getOffsetX() && this.getOffsetY() == direction.getOffsetY();
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
