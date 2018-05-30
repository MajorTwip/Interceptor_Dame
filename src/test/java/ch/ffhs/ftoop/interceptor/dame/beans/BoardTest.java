package ch.ffhs.ftoop.interceptor.dame.beans;

import org.apache.commons.math3.exception.OutOfRangeException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testConstructor() throws Exception {
        int testSet[][] = {{4, 4, 0}, {255, 255, 0}, {3, 3, 1}, {256, 256, 1}, {-5, -5, 1}};

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Board b;
        for (int i[] : testSet) {
            try {
                b = new Board(i[0], i[1]);
                if (i[2] == 1) {
                    throw (new Exception("Error was expected but has not been thrown. Test values: " + Arrays.toString(i)));
                }
                assertEquals(i[0], b.getMaxX());
                assertEquals(i[1], b.getMaxY());
            } catch (OutOfRangeException e) {
                if (i[2] != 1) {
                    throw (e);
                }
            }
        }
    }

    @Test
    public void testAddStone() throws Exception {
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Board b = new Board(4, 4);
        int testSet[][] = { //testSet: 0=x, 1=y, 2=error(0=no error, 1=RuntimeException, 2=OutOfRangeException)
                {0, 0, 0}, {b.getMaxX(), b.getMaxY(), 0}, {b.getMaxX() + 1, b.getMaxY() + 1, 2},
                {2, 2, 0}, {2, 2, 1}
        };

        for (int i[] : testSet) {
            try {
                b.addStone(new Stone(new Coordinate(i[0], i[1]), false, false));
                if (i[2] > 0) {
                    throw (new Exception("Error was expected but has not been thrown. Test values: " + Arrays.toString(i)));
                }
            } catch (OutOfRangeException oe) {
                if (i[2] != 2) {
                    throw (oe);
                }
            } catch (RuntimeException re) {
                if (i[2] != 1) {
                    throw (re);
                }
            }
        }
    }

    @Test
    public void testRemoveStoneAt() {
        Board b = new Board(4, 4);
        b.addStone(new Stone(new Coordinate(2, 2), false, false));
        assertFalse(b.fieldFree(new Coordinate(2, 2)));
        b.removeStoneAt(new Coordinate(2, 2));
        assertTrue(b.fieldFree(new Coordinate(2, 2)));
    }

    @Test
    public void testFieldFree() {
        Board b = new Board(4, 4);
        for (int i = 0; i <= b.getMaxX(); i++) {
            for (int k = 0; k <= b.getMaxY(); k++) {
                assertTrue(b.fieldFree(new Coordinate(i, k)));
            }
        }
        b.addStone(new Stone(new Coordinate(2, 2), false, false));

        assertFalse(b.fieldFree(new Coordinate(2, 2)));
        assertTrue(b.fieldFree(new Coordinate(1, 1)));
    }

    @Test
    public void testGetStoneAt1() {
        Board b = new Board(4, 4);
        Stone s = new Stone(new Coordinate(2, 2), false, false);
        b.addStone(new Stone(new Coordinate(1, 1), false, false));
        b.addStone(s);

        assertEquals(s,
                b.getStoneAt(new Coordinate(2, 2)));
        assertNotEquals(s,
                b.getStoneAt(new Coordinate(1, 1)));
    }

    @Test(expected = RuntimeException.class)
    public void testGetStoneAt2() {
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Board b = new Board(4, 4);
        b.addStone(new Stone(new Coordinate(2, 2), false, false));
        b.getStoneAt(new Coordinate(1, 1));
    }

    @Test
    public void testDefaultFunction() throws Exception{
        Board b = new Board(4, 6);
        Stone s = new Stone(new Coordinate(1, 1), false, false);

        b.setOwnTurn(true);
        assert(b.getOwnTurn());
        b.setOwnTurn(false);
        assert(!b.getOwnTurn());
        assertEquals(4, b.getMaxX());
        assertEquals(6, b.getMaxY());

        b.setStoneEnforcement(s);
        assertEquals(s, b.getStoneEnforcement().orElseThrow(IllegalStateException::new));
        b.setStoneEnforcementDisabled();
        assertFalse(b.getStoneEnforcement().isPresent());
    }
}