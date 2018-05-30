package ch.ffhs.ftoop.interceptor.dame.beans;

import org.apache.commons.math3.exception.OutOfRangeException;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void testConstruct1() {
        Coordinate c = new Coordinate(1, 3);
        assertEquals(c.getX(), 1);
        assertEquals(c.getY(), 3);
    }

    @Test(expected = OutOfRangeException.class)
    public void testConstruct2() {
        new Coordinate(-1, 3);
    }

    @Test(expected = OutOfRangeException.class)
    public void testConstruct3() {
        new Coordinate(0, -10);
    }

    @Test
    public void testCreateCoordinate1() {
        assertTrue(Coordinate.createCoordinate(1, 3).isPresent());
        assertFalse(Coordinate.createCoordinate(-1, 3).isPresent());
        assertFalse(Coordinate.createCoordinate(1, -3).isPresent());
        assertEquals("4/5", Coordinate.createCoordinate(4, 5).orElseThrow(IllegalStateException::new).toString());
    }

    @Test
    public void testCreateCoordinate2() throws Exception {
        //testSet second dimenstion: 0=coordX, 1=coordY, 2=offsetX, 3=offsetY, 4=factor, 5=resultX, 6=resultY, 7=error expected)
        int testSet[][] = {
                {1, 2, 1, 1, 1, 2, 3, 0}, {1, 4, 1, 1, 1, 2, 5, 0}, {1, 2, 1, 2, 1, 2, 4, 0}, //usual positive tests
                {9, 5, 4, 8, 2, 17, 21, 0}, {1, 1, -1, -1, 1, 0, 0, 0}, {1, 1, -1, -1, 0, 1, 1, 0}, //positive tests with factor/negatives
                {1, 1, -4, 4, 1, -3, 5, 1}, {1, 1, 4, -4, 1, 5, -3, 1}, {1, 1, -1, -1, 2, -1, -1, 1} //negative tests with factor/negatives
        };

        Coordinate c;
        for (int i[] : testSet) {
            try {
                c = createCoordinateWithOffset(i[0], i[1], i[2], i[3], i[4]);
                assertEquals(i[5] + "/" + i[6], c.toString());

                if (i[7] == 1) {
                    throw (new Exception("Error was expected but has not been thrown. Test values: " + Arrays.toString(i)));
                }
            } catch (IllegalArgumentException e) {
                if (i[7] != 1) {
                    throw (e);
                }
            }
        }
    }

    @Test
    public void testGetCoordinateBetween() {
        int testSet[][] = { //testSet:  0=x1, 1=y1, 2=x2, 3=y3, 4=resultX, 5=resultY, 6=null expected
                {0, 2, 2, 2, 1, 2, 0}, {0, 2, 0, 0, 0, 1, 0}, {5, 4, 5, 0, 5, 2, 0},
                {0, 3, 2, 2, 0, 0, 1}, {1, 2, 4, 2, 0, 0, 1}, {0, 0, 3, 5, 0, 0, 1}
        };
        Optional<Coordinate> c;
        for (int i[] : testSet) {
            c = Coordinate.getCoordinateBetween(new Coordinate(i[0], i[1]), new Coordinate(i[2], i[3]));
            if (i[6] == 1) {
                assertFalse(c.isPresent());
            } else {
                assertEquals(i[4] + "/" + i[5], c.orElseThrow(IllegalStateException::new).toString());
            }
        }
    }

    @Test
    public void testDefaultFunctions() {
        Coordinate c1 = new Coordinate(1, 1);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(1, 1);
        assertNotEquals(c1, c2);
        assertEquals(c1, c3);
    }

    private Coordinate createCoordinateWithOffset(int x, int y, int offsetX, int offsetY, int factor) throws IllegalArgumentException {
        Optional<Coordinate> c = Coordinate.createCoordinate(
                new Coordinate(x, y), new Direction(offsetX, offsetY, "test"), factor);
        if (!c.isPresent()) {
            throw (new IllegalArgumentException("Some arguments were wrong"));
        }

        return c.get();
    }

}