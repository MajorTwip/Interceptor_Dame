package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

class Turn {
    private Stone stone;
    private Coordinate coordinate;
    private boolean mustFail;
    private boolean ownTurn;

    Turn(Stone stone, int x, int y, boolean ownTurn, boolean mustFail) {
        this.stone = stone;
        this.coordinate = new Coordinate(x, y);
        this.mustFail = mustFail;
        this.ownTurn = ownTurn;

    }

    Stone getStone() {
        return stone;
    }

    Coordinate getCoord() {
        return coordinate;
    }

    boolean getOwnTurn() {
        return ownTurn;
    }

    boolean mustFail() {
        return mustFail;
    }

    @Override
    public String toString() {
        return "Turn: " + stone + " moves to " + coordinate;
    }

}

public class RuleCheckTest {
    private Board board;
    private Stone stone11, stone22, stone33, stone02, stone44;

    @Before
    public void setUp() {
        board = new Board(4, 4);
        stone11 = new Stone(new Coordinate(1, 1), false, false);
        stone22 = new Stone(new Coordinate(2, 2), true, false);
        stone33 = new Stone(new Coordinate(3, 3), true, true);
        stone02 = new Stone(new Coordinate(0, 2), false, false);
        stone44 = new Stone(new Coordinate(4, 4), false, false);
        board.addStone(stone11);
        board.addStone(stone22);
        board.addStone(stone33);
        board.addStone(stone02);
        board.addStone(stone44);

    }

    @Test
    public void testStoneCanBeSelected() {
        Board board = new Board(4, 4);

        assertFalse(RuleCheck.stoneCanBeSelected(board, stone11));
        assertTrue(RuleCheck.stoneCanBeSelected(board, stone22));
        assertTrue(RuleCheck.stoneCanBeSelected(board, stone33));

        board.setStoneEnforcement(stone22);
        assertFalse(RuleCheck.stoneCanBeSelected(board, stone33));

        board.setStoneEnforcementDisabled();
        assertTrue(RuleCheck.stoneCanBeSelected(board, stone33));

        board.setOwnTurn(!board.getOwnTurn());
        assertTrue(RuleCheck.stoneCanBeSelected(board, stone11));
        assertFalse(RuleCheck.stoneCanBeSelected(board, stone22));
    }

    @Test
    public void testGetTurnIsLegal() throws Exception {
        ArrayList<Turn> testTurns = new ArrayList<>();
        testTurns.add(new Turn(stone11, 0, 0, false, true)); //no reverse direction allowed
        testTurns.add(new Turn(stone11, 2, 2, false, true)); //field is not free
        testTurns.add(new Turn(stone11, 2, 1, false, true)); //only diagonal allowed
        testTurns.add(new Turn(stone11, 1, 2, false, true)); //only diagonal allowed
        testTurns.add(new Turn(stone02, 2, 4, false, true)); //too far away
        testTurns.add(new Turn(stone02, 1, 3, false, false)); //move
        testTurns.add(new Turn(stone22, 3, 1, true, true)); //no move if kill is possible
        testTurns.add(new Turn(stone22, 2, 2, true, true)); //field is not free
        testTurns.add(new Turn(stone22, 1, 1, true, true)); //field is not free
        testTurns.add(new Turn(stone22, 0, 0, true, false)); //kill
        testTurns.add(new Turn(stone44, 6, 4, false, true)); //outside board
        testTurns.add(new Turn(stone44, 6, 6, false, true)); //outside board
        testTurns.add(new Turn(stone44, 4, 6, false, true)); //outside board


        for (Turn turn : testTurns) {
            board.setOwnTurn(turn.getOwnTurn());
            if (RuleCheck.getTurnIsLegal(board, turn.getStone(), turn.getCoord()) == turn.mustFail()) {
                throw (new Exception("RuleCheck.getTurnIsLegal returned " + !turn.mustFail() + " for " + turn));
            }
        }
    }

    @Test
    public void testCanKillWithTurn() {
        assertTrue(RuleCheck.canKillWithTurn(board, stone22, new Coordinate(0, 0))); //kill
        assertFalse(RuleCheck.canKillWithTurn(board, stone22, new Coordinate(3, 1))); //no field between
        assertFalse(RuleCheck.canKillWithTurn(board, stone22, new Coordinate(1, 0))); //no field between
        assertFalse(RuleCheck.canKillWithTurn(board, stone22, new Coordinate(4, 0))); //no enemy between
    }

    @Test
    public void testGetAllowedCoordinates() {
        ArrayList<Coordinate> allowedCoordinates;

        allowedCoordinates = new ArrayList<>();
        board.setOwnTurn(true);
        assertTrue(RuleCheck.getAllowedCoordinates(board, stone22, allowedCoordinates));
        assertTrue(allowedCoordinates.contains(new Coordinate(0, 0)) && allowedCoordinates.size() == 1);

        allowedCoordinates = new ArrayList<>();
        board.setOwnTurn(false);
        assertFalse(RuleCheck.getAllowedCoordinates(board, stone11, allowedCoordinates));
        assertEquals(0, allowedCoordinates.size());

        allowedCoordinates = new ArrayList<>();
        board.setOwnTurn(false);
        assertFalse(RuleCheck.getAllowedCoordinates(board, stone02, allowedCoordinates));
        assertTrue(allowedCoordinates.contains(new Coordinate(1, 3)));
        assertEquals(1, allowedCoordinates.size());

        allowedCoordinates = new ArrayList<>();
        board.setOwnTurn(false);
        assertFalse(RuleCheck.getAllowedCoordinates(board, stone44, allowedCoordinates));
        assertEquals(0, allowedCoordinates.size());

        allowedCoordinates = new ArrayList<>();
        board.setOwnTurn(true);
        assertFalse(RuleCheck.getAllowedCoordinates(board, stone33, allowedCoordinates));
        assertTrue(allowedCoordinates.contains(new Coordinate(2, 4)));
        assertTrue(allowedCoordinates.contains(new Coordinate(4, 2)));
        assertEquals(2, allowedCoordinates.size());
    }

    @Test
    public void testCanKillEnemy1() {
        board.setOwnTurn(false);
        assertFalse(RuleCheck.canKillEnemy(board));

        board.setOwnTurn(true);
        assertTrue(RuleCheck.canKillEnemy(board));
    }

    @Test
    public void testCanKillEnemy2() {
        board.setOwnTurn(true);
        assertTrue(RuleCheck.canKillEnemy(board, stone22));
        assertFalse(RuleCheck.canKillEnemy(board, stone33));
        assertFalse(RuleCheck.canKillEnemy(board, stone11));

        board.setOwnTurn(false);
        assertFalse(RuleCheck.canKillEnemy(board, stone22));
    }
}