package ch.ffhs.ftoop.interceptor.dame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Dame extends Application{
	GUI gui;
	Backend backend;
	
	
	public static void main(String[] args) {
		launch(args);
		
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		gui = new GUI(backend, primaryStage);
		backend = new Backend(gui);
		
		gui.showMenu();
	}
	
	
	

}
