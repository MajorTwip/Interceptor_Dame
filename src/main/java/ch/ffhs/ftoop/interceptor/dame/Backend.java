package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.GameMode;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;

/**
 * Backend to the Dame-Game
 * 
 * 
 * @author Simon
 * @version 0.1
 */
public class Backend implements DameBackendInterface{
	GUI gui;
	
	public Backend(GUI gui) {
		
	}

	@Override
	public void startNewGame(GameMode mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTurnIsLegal(Stone stone, Coordinate coordinate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyTurn(Stone stone, Coordinate coordinate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Board getActualBoard() {
		// TODO Auto-generated method stub
		return null;
	}
}
