package ch.ffhs.ftoop.interceptor.dame.beans;

import java.util.ArrayList;

/**
 * represents a direction of the field
 *
 * @author simcrack
 * @version 20180428
 */
public class Direction {
    int offsetX;
    int offsetY;
    String description;

    /**
     * instantiates a ne Direction object
     *
     * @param offsetX     the offset of the x coordinate
     * @param offsetY     the offset of the y coordinate
     * @param description the description of the direction
     */
    Direction(int offsetX, int offsetY, String description) {
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

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
