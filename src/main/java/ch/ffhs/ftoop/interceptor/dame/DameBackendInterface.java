/**
 * 
 */
package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.GameMode;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;

/**
 * @author MajorTwip
 * @version 0.1
 * 
 * Interface to be applied by the game-backend
 * It contains methods to be called by the GUI
 */

public interface DameBackendInterface {
	/**
	 * Start a new game.
	 * 
	 * Should call GUI's method showBoard(Board board), showMessageBox(MessageBox msg) with some Welcometext
	 *  and initiateTurn();
	 * @param mode Enum GameType (10x10, 8x8, etc)
	 */
	public void startNewGame(GameMode mode);
	
	/**
	 * Exits the game. Yields eventually an Alert in am MessageBox 
	 */
	public void quitGame();
	
	/**
	 * Checks if it would be legal to move stone Stone to a new Coodinate
	 * @param stone Stone to move
	 * @param coordinate Coordinate to move to
	 * @return Boolean, true if move would be legal
	 */
	public boolean getTurnIsLegal(Stone stone,Coordinate coordinate);
	
	/**
	 * applies a move, move stone Stone to a new Coodinate
	 * @param stone Stone to move
	 * @param coordinate Coordinate to move to
	 * @return Boolean, true if move was successful
	 */
	public boolean applyTurn(Stone stone, Coordinate coordinate);
	
	/**
	 * Returns Board with the actual state of the game
	 * @return Board with actual state
	 */
	public Board getActualBoard();
}
