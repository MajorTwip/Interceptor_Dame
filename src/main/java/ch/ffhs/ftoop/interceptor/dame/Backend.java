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
	
	public Backend() {	
	}
	
	public void setGUI(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void startNewGame(GameMode mode) {
		// TODO Auto-generated method stub
		
		//TEST
		Board testboard = new Board(7,7);
		testboard.add(new Stone(new Coordinate(2,4),true,false));
		gui.showBoard(testboard);
	}

	@Override
	public void quitGame() {
		// TODO Auto-generated method stub
		System.exit(0);
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
