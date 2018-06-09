package ch.ffhs.ftoop.interceptor.dame;


import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.Direction;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * check turns on a board
 * is used by Backend and AI
 *
 * @author simcrack
 */
class RuleCheck {
	interface TurnCheck {
		boolean check(Board board, Stone stone, Coordinate coordinate);
	}

	private static TurnCheck isTurnBackward = (Board b, Stone s, Coordinate c) -> s.getCoordinate().getY() < c.getY();
	private static TurnCheck isTurnForward = (Board b, Stone s, Coordinate c) -> s.getCoordinate().getY() > c.getY();
	private static TurnCheck isTurnDirAllowed = (Board b, Stone s, Coordinate c) -> {
		if (s.getIsQueen()) {
			return isTurnForward.check(b, s, c) || isTurnBackward.check(b, s, c);
		}
		if (s.getIsOwn()) {
			return isTurnForward.check(b, s, c);
		} else {
			return isTurnBackward.check(b, s, c);
		}
	};
	private static TurnCheck isTurnOnBoard = (Board b, Stone s, Coordinate c) -> c.getX() >= 0 &&
			c.getY() >= 0 &&
			c.getX() <= b.getMaxX() &&
			c.getY() <= b.getMaxY();
	private static TurnCheck isTurnBlockedByEnemy = (Board b, Stone s, Coordinate c) -> {
		try {
			return b.getStoneAt(c).getIsOwn() != s.getIsOwn();
		} catch (RuntimeException e) {
			return false;
		}
	};
	private static TurnCheck isTurnBlockedByMe = (Board b, Stone s, Coordinate c) -> {
		try {
			return b.getStoneAt(c).getIsOwn() == s.getIsOwn();
		} catch (RuntimeException e) {
			return false;
		}
	};

	/**
	 * checks if a stone can be selected (is only possible if it is the turn of the stone owner)
	 * if the is a TurnEnforcement wi
	 *
	 * @param board a reference to the board on which the stone currently is
	 * @param stone the stone for which shall be tested
	 * @return true if the stone belongs to the current player, false if not
	 */
	static boolean stoneCanBeSelected(Board board, Stone stone) {
		if (board.getStoneEnforcement().isPresent()) {
			//if there is a stone with which must be moved it is the only which can be selected
			return board.getStoneEnforcement().get() == stone;
		} else {
			//if not the selected stone must only be in the players color
			return stone.getIsOwn() == board.getOwnTurn();
		}
	}

	/**
	 * Checks if it would be legal to move stone Stone to a new Coodinate
	 *
	 * @param stone      Stone to move
	 * @param coordinate Coordinate to move to
	 * @param board      Board on which shall be moved
	 * @return Boolean, true if move would be legal
	 */
	static boolean getTurnIsLegal(Board board, Stone stone, Coordinate coordinate) {
		ArrayList<Coordinate> allowedCoordinates = new ArrayList<>();
		//if this stone can not be used to kill an enemy stone but there is an other stone which can do this,
		// the turn with tis stone is not allowed
		if (!getAllowedCoordinates(board, stone, allowedCoordinates)
				&& canKillEnemy(board)) {
			return false;
		}
		return allowedCoordinates.contains(coordinate);
	}

	/**
	 * determines if a turn would kill an enemy
	 *
	 * @param board      on which the turn has to be checked
	 * @param stone      to move
	 * @param coordinate where to move to
	 * @return true if an other stone can be killed with this turn
	 */
	static boolean canKillWithTurn(Board board, Stone stone, Coordinate coordinate) {
		Optional<Coordinate> fieldBetween = Coordinate.getCoordinateBetween(stone.getCoordinate(), coordinate);
		if (fieldBetween.isPresent()) {
			if (board.fieldFree(fieldBetween.get())) {
				//if field is free there is no stone which can be killed (is usually not possible)
				return false;
			} else {
				//if field is not free it can be killed only if the stone between has the other color (is an enemy)
				return board.getStoneAt(fieldBetween.get()).getIsOwn() != stone.getIsOwn();
			}
		} else {
			//if there is no field between the stone and the new coordinate the functin returns always false
			return false;
		}

	}

	/**
	 * Gets all allowed new coordinates for a given stone
	 *
	 * @param board       Board on which the stone is present
	 * @param stone       Stone for which the allowed coordinates shall be returned
	 * @param coordinates ArrayList of coordinates ByRef in which allowed coordinates are written
	 * @return true if an enemy can be killed with this stone, false if not
	 */
	static boolean getAllowedCoordinates(Board board, Stone stone, List<Coordinate> coordinates) {
		return getAllowedCoordinates(board, stone, board.getOwnTurn(), coordinates);
	}

	/**
	 * Gets all allowed new coordinates for a given stone
	 * overloads getAllowedCoordinates for cases where the possibilities of the enemy shall be tested
	 *
	 * @param board       Board on which the stone is present
	 * @param stone       Stone for which the allowed coordinates shall be returned
	 * @param own         which turn it is (usually defined by board but must also be possible to change view)
	 * @param coordinates ArrayList of coordinates ByRef in which allowed coordinates are written
	 * @return true if an enemy can be killed with this stone, false if not
	 */
	private static boolean getAllowedCoordinates(Board board, Stone stone, boolean own, List<Coordinate> coordinates) {
		assert (coordinates.size() == 0);
		if (own != stone.getIsOwn()) {
			return false;
		}

		ArrayList<Coordinate> moveCoordinates = new ArrayList<>();
		ArrayList<Coordinate> killCoordinates = new ArrayList<>();

		for (Direction direction : Direction.getAllDirections()) {
			Coordinate.createCoordinate(stone.getCoordinate(), direction, 1)
					.ifPresent(moveCoordinates::add);
		}

		Iterator<Coordinate> itr = moveCoordinates.iterator();
		while (itr.hasNext()) {
			Coordinate coordinate = itr.next();
			if (!isTurnOnBoard.check(board, stone, coordinate)
					|| !isTurnDirAllowed.check(board, stone, coordinate)
					|| isTurnBlockedByMe.check(board, stone, coordinate)) {
				itr.remove();
				continue;
			}
			if (isTurnBlockedByEnemy.check(board, stone, coordinate)) {
				Direction direction = Direction.getDirection(stone.getCoordinate(), coordinate).orElse(new Direction(0, 0, "Null"));
				Coordinate.createCoordinate(stone.getCoordinate(), direction, 2)
						.ifPresent((Coordinate c) -> {
							if (board.fieldFree(c) && isTurnOnBoard.check(board, stone, c)) {
								killCoordinates.add(c);
							}
						});
				itr.remove(); //if the original move coordinate was blocked it shall be removed
			}
		}

		if (killCoordinates.isEmpty()) {
			coordinates.addAll(moveCoordinates);
			return false;
		} else {
			coordinates.addAll(killCoordinates);
			return true;
		}

	}

	/**
	 * Checks if there is a Stone which can kill an enemy (if so it has to be moved with it)
	 *
	 * @param board board on which shall be searched for stones which could kill enemies
	 * @return true if there is a Stone which can kill an enemy, false if not
	 */
	static boolean canKillEnemy(Board board) {
		for (Stone stone : board) {
			if (canKillEnemy(board, stone)) {
				return true;
			}
		}
		return false;
	}

	static boolean canKillEnemy(Board board, Stone stone) {
		ArrayList<Coordinate> dummyArray = new ArrayList<>(); //unnecessary but a must have for getAllowedCoordinates
		return (stone.getIsOwn() == board.getOwnTurn()
				&& getAllowedCoordinates(board, stone, dummyArray));
	}

	/**
	 * Checks if the current player can move with any stone
	 * is used to detect if the user lost because every stone is blocked
	 *
	 * @param board which shall be tested
	 * @param isOwn defines player for which the test shall be done
	 * @return true if a stone can be moved, false if every stone is blocked
	 */
	static boolean isPlayerBlocked(Board board, boolean isOwn) {
		List<Coordinate> list = new ArrayList<>();
		for (Stone stone : board) {
			//if stone is not board.getOwn() or cant be moved, the function returns an empty list
			getAllowedCoordinates(board, stone, isOwn, list);
			if (list.size() > 0) {
				return false;
			}
		}
		return true;
	}

}
