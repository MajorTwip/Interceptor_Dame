package ch.ffhs.ftoop.interceptor.dame;

import java.util.Locale;
import java.util.ResourceBundle;

import ch.ffhs.ftoop.interceptor.dame.beans.GameMode;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GUIMenu extends VBox {
	ResourceBundle texts;
	Backend backend;
	
	public GUIMenu(Backend backend) {
		super();
		this.backend = backend;
		texts = ResourceBundle.getBundle("gui_texts", new Locale("EN"));
		
		Button btn_startgame = new Button();
		btn_startgame.setText(texts.getString("btn_startgame"));	
		btn_startgame.setOnAction(e->backend.startNewGame(GameMode.Singleplayer8X8));
		
		Button btn_quitgame = new Button();
		btn_quitgame.setText(texts.getString("btn_quitgame"));
		btn_quitgame.setOnAction(e->backend.quitGame());
		
				
        this.getChildren().add(btn_startgame);
        this.getChildren().add(btn_quitgame);

	}
	
	
	

}
