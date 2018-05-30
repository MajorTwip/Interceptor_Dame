package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BackendTest {
    private Backend backend;
    private Stone stone11, stone22, stone33, stone02, stone44;

    @Before
    public void setUp() {
        backend = new Backend();
        backend.setActualBoard(new Board(4, 4));
        stone11 = new Stone(new Coordinate(1, 1), false, false);
        stone22 = new Stone(new Coordinate(2, 2), true, false);
        stone33 = new Stone(new Coordinate(3, 3), true, true);
        stone02 = new Stone(new Coordinate(0, 2), false, false);
        stone44 = new Stone(new Coordinate(4, 4), false, true);
        backend.getActualBoard().addStone(stone11);
        backend.getActualBoard().addStone(stone22);
        backend.getActualBoard().addStone(stone33);
        backend.getActualBoard().addStone(stone02);
        backend.getActualBoard().addStone(stone44);
    }

    @Test
    public void testStoneCanBeSelected() {
        assertFalse(backend.stoneCanBeSelected(stone11));
        assertTrue(backend.stoneCanBeSelected(stone22));

        backend.getActualBoard().setOwnTurn(false);
        assertTrue(backend.stoneCanBeSelected(stone11));
        assertFalse(backend.stoneCanBeSelected(stone22));
    }

    @Test
    public void testDefaultFunctions() {
        assertFalse(backend.isSinglePlayer());
    }
}