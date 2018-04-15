package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.MessageBox;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI implements DameGUIInterface {
	
	Backend backend;
	Stage stage;
	Scene menu;
	Scene game;
	
	public GUI(Backend backend,Stage stage) {
		this.backend = backend;
		this.stage = stage;
		
		stage.setTitle("DAME by Simon Reichenbach & Yvo von K�nel");
	}
	

	@Override
	public void showMenu() {
		stage.setScene(new Scene(new GUIMenu()));
		stage.show();
	}

	@Override
	public void showBoard(Board bard) {
		stage.setScene(new Scene(new GUIBoard()));
		stage.show();
	}

	@Override
	public void closeApplication() {
		backend.quitGame();
	}

	@Override
	public void initiateTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean showMessageBox(MessageBox msgbox) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void animateMove(Stone stone, Coordinate coordinate) {
		// TODO , firstly just redrawing
	}

	@Override
	public void animateStoneRemove(Stone stone) {
		// TODO , firstly just redrawing
	}
	
	public void redraw() {
		backend.getActualBoard();
	}

}
