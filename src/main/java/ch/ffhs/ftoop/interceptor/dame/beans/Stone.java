package ch.ffhs.ftoop.interceptor.dame.beans;

import java.util.Objects;

/**
 * A stone, either white or black
 *
 * @author MajorTwip
 */
public class Stone {
    private Coordinate coordinate;
    private Boolean isOwn;
    private Boolean isQueen;

    /**
     * Initiates a playstone
     *
     * @param coordinate Coordinate of the stone
     * @param isOwn      true if own stone, false if enemy
     * @param isQueen    true if Dame, false for normal stones
     */
    public Stone(Coordinate coordinate, Boolean isOwn, Boolean isQueen) {
        this.coordinate = coordinate;
        this.isOwn = isOwn;
        this.isQueen = isQueen;
    }

    /**
     * Setter isOwn
     *
     * @param isOwn true if own stone, false if enemy
     */
    public void setIsOwn(Boolean isOwn) {
        this.isOwn = isOwn;
    }

    /**
     * Setter isQueen
     *
     * @param isQueen true if Dame, false for normal stones
     */
    public void setIsQueen(Boolean isQueen) {
        this.isQueen = isQueen;
    }

    /**
     * Getter isOwn
     *
     * @return true if own stone, false if enemy
     */
    public Boolean getIsOwn() {
        return isOwn;
    }

    /**
     * Getter isQueen
     *
     * @return true if Dame, false for normal stones
     */
    public Boolean getIsQueen() {
        return isQueen;
    }

    /**
     * Getter Coordinate
     *
     * @return Coordinate of the stone
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Setter Coordinate
     *
     * @param coordinate Coordinate of the stone
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        String returnString = "Stone at coord " + this.getCoordinate().getX() + "/" + this.getCoordinate().getY();
        if (this.isOwn) {
            returnString += " is an Players";
        } else {
            returnString += " is an Enemys";
        }
        if (this.isQueen) {
            returnString += " Queen";
        } else {
            returnString += " normal Stone";
        }
        return returnString;
    }

    /**
     * Common equals-method
     *
     * @param obj Stone to compare
     * @return true if equal values
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Stone)) return false;

        Stone s = (Stone) obj;
        return this.getCoordinate().equals(s.getCoordinate())
                && this.getIsOwn() == s.getIsOwn()
                && this.getIsQueen() == s.getIsQueen();
    }

    /**
     * Common hashCode-method
     *
     * @return true if equal values
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getCoordinate(), this.getIsOwn(), this.getIsQueen());
    }

}
