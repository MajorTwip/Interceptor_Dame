package ch.ffhs.ftoop.interceptor.dame;

import java.util.Locale;
import java.util.ResourceBundle;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.GameMode;
import ch.ffhs.ftoop.interceptor.dame.beans.MessageBox;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI implements DameGUIInterface {
	
	Backend backend;
	Stage stage;
	Scene menu;
	Scene game;
	
	ResourceBundle texts;
	
	public GUI(Stage stage) {
		this.stage = stage;
		texts = ResourceBundle.getBundle("gui_texts", new Locale("EN"));

		
		stage.setTitle(texts.getString("title"));
		//stage.setMaximized(true);
		//stage.setMinWidth(640);
		//stage.setMinHeight(640);
	}
	
	private MenuBar getMenu() {
		MenuBar menubar = new MenuBar();
		
		Menu menuGame = new Menu("Game");
		
		MenuItem menuItemNewGame = new MenuItem("New Game");
		menuItemNewGame.setOnAction(e->backend.startNewGame(GameMode.Singleplayer8X8));
		
		MenuItem menuItemQuit = new MenuItem("Quit");
		menuItemQuit.setOnAction(e-> backend.quitGame());
		
		menuGame.getItems().addAll(menuItemNewGame,menuItemQuit);
		
		menubar.getMenus().addAll(menuGame);
		
		return menubar;
	}
	
	public void setBackend(Backend backend) {
		this.backend = backend;
	}
	

	@Override
	public void showMenu() {
		stage.setScene(new Scene(new GUIMenu(backend)));
		stage.show();
	}

	@Override
	public void showBoard(Board board) {
		Scene scene = new Scene(new VBox());
		((VBox)scene.getRoot()).getChildren().add(getMenu());
		
		GUIBoard guiboard = new GUIBoard(board);
		((VBox)scene.getRoot()).getChildren().add(guiboard);
		
		stage.setScene(scene);
		stage.show();
		guiboard.draw();
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
