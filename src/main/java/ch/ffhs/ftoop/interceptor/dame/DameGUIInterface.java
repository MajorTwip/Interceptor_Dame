package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.MessageBox;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;

/**
 * Interface to be implemented by the GUI
 * 
 * 
 * @author MajorTwip
 * @version 0.1
 */
public interface DameGUIInterface {
	
	/**
	 * Starts the application if not already running and shows the menu
	 */
	public void showMenu();
	
	/**
	 * Starts the application if not already running and shows the game
	 * @param bard Board with the actual Gamestate
	 */
	public void showBoard(Board bard);
	
	/**
	 * Close the application
	 */
	public void closeApplication();
	
	/**
	 * Indicates the player's turn.
	 */
	public void initiateTurn();
	
	/**
	 * Shows a MessageBox. Returns true if Confirm/OK was clicked
	 * @param msgbox MessageBox to be shown
	 * @return Boolean, true if OK/Confirm was clicked
	 */
	public boolean showMessageBox(MessageBox msgbox);
	
	/**
	 * Animates a move. Only visual effects, no changes to the board will be applied.
	 * Reloads the actual gamestate with getActualBoard() afterwards
	 * 
	 * @param stone Stone to move
	 * @param coordinate Coordinate to move to
	 */
	public void animateMove(Stone stone, Coordinate coordinate);
	
	/**
	 * Animates the disappearance of a stone. Only visual effect, no changes will be applied
	 * Reloads the actual gamestate with getActualBoard() afterwards
	 * 
	 * @param stone Stone to remove
	 */
	public void animateStoneRemove(Stone stone);
}
