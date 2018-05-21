package ch.ffhs.ftoop.interceptor.dame;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 * Dame-Spiel
 * 
 * @author Yvo von Känel (MajorTwip)
 * @author Simon Reichenbach (simcrack)
 */
public class Dame extends Application{
	GUI gui;
	Backend backend;
	
	/**
	 * startet die Application gemäss standarts JavaFX
	 * @param args ignored
	 */
	public static void main(String[] args) {
		launch(args);

	}


	@Override
	/**
	 * Create a backend and a gui and link them together
	 * Called by launch() in main
	 * @param primaryStage mainstage created by JavaFX
	 */
	public void start(Stage primaryStage) throws Exception {
		gui = new GUI(primaryStage);
		backend = new Backend();
		
		backend.setGUI(gui);
		gui.setBackend(backend);
		
		gui.showMenu();
	}
	
	
	

}
