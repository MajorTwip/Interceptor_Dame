package ch.ffhs.ftoop.interceptor.dame.beans;

import org.junit.Test;

import static org.junit.Assert.*;

public class StoneTest {

    @Test
    public void testDefaultFunction() {
        Stone s1 = new Stone(new Coordinate(1, 1), false, false);
        Stone s2 = new Stone(new Coordinate(2, 1), false, false);
        Stone s3 = new Stone(new Coordinate(1, 2), false, false);
        Stone s4 = new Stone(new Coordinate(1, 1), true, false);
        Stone s5 = new Stone(new Coordinate(2, 2), false, true);
        Stone s6 = new Stone(new Coordinate(1, 1), false, false);

        assertEquals(true, s5.getIsQueen());
        assertEquals(true, s4.getIsOwn());
        assertEquals(new Coordinate(1, 1), s1.getCoordinate());

        assertNotEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);
        assertNotEquals(s1, s5);
        assertEquals(s1, s6);

        s5.setCoordinate(s1.getCoordinate());
        assertEquals(s1.getCoordinate(), s5.getCoordinate());

        s2.setIsOwn(true);
        assertEquals(true, s2.getIsOwn());

        s2.setIsQueen(true);
        assertEquals(true, s2.getIsQueen());
    }

}