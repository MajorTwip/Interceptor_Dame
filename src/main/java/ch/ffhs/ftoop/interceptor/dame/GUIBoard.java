package ch.ffhs.ftoop.interceptor.dame;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class GUIBoard extends StackPane{

	public GUIBoard() {
		super();
		
		Button btn = new Button();
        btn.setText("Say 'Hello World'");	
        this.getChildren().add(btn);
	}
}
