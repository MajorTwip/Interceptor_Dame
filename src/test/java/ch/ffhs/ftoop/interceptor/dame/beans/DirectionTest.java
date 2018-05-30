package ch.ffhs.ftoop.interceptor.dame.beans;

import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void testGetDirection() throws Exception {
        int testSet[][] = { //test Values: 0=x1, 1=y1, 2=x2, 3=y2, 4=resultX, 5=resultY, 6=error expected
                {0, 0, 1, 1, 1, 1, 0}, {0, 0, 3, 3, 1, 1, 0}, {1, 0, 0, 1, -1, 1, 0},
                {2, 6, 4, 4, 1, -1, 0}, {0, 0, 1, 2, 0, 0, 1}, {0, 0, 2, 1, 0, 0, 1}
        };

        for (int i[] : testSet) {
            try {
                assertEquals(new Pair<Integer, Integer>(i[4], i[5]),
                        Direction.getDirection(new Coordinate(i[0], i[1]), new Coordinate(i[2], i[3]))
                                .orElseThrow(IllegalArgumentException::new)
                                .getOffset()
                );
                if (i[6] == 1) {
                    throw (new Exception("Error was expected but has not been thrown. Test values: " + Arrays.toString(i)));
                }
            } catch (IllegalArgumentException e) {
                if (i[6] != 1) {
                    throw (e);
                }
            }

        }
    }

    @Test
    public void testDefaultFunctions() {
        Direction d1 = new Direction(1, 1, "test1");
        Direction d2 = new Direction(1, 0, "test2");
        Direction d3 = new Direction(1, 1, "test3");
        assertEquals(d1.getOffset(), d3.getOffset());
        assertEquals(d1.getOffsetX(), d3.getOffsetY());
        assertEquals(d1.getOffsetX(), d3.getOffsetY());
        assertNotEquals(d1.getOffset(), d2.getOffset());
    }

}