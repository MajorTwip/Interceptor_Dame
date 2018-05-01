package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Stone;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GUIStone extends StackPane{
	GUIBoard board;
	Stone stone;
	
	GUIStone(GUIBoard board, Stone stone){
		super();
		this.board=board;
		this.stone = stone;
		GridPane.setHgrow(this, Priority.ALWAYS);
		
		//Add Circle
		
		Circle circle = new Circle();
		circle.radiusProperty().bind(this.heightProperty().divide(3));
		Color color = Color.BLACK;
		if(stone.getIsOwn()) color = Color.WHITE;
		circle.setFill(color);
		circle.setStroke(Color.GRAY);
		this.getChildren().add(circle);
		
		if(stone.getIsQueen()) {
			Text queen = new Text("Q");
			queen.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
			queen.setFill(Color.WHITE);
			queen.setStroke(Color.BLACK);
			this.getChildren().add(queen);
		}
		
		this.setOnMouseClicked(e->board.setClickedStone(this));
		
		board.add(this, stone.getCoordinate().getX(), stone.getCoordinate().getY());
		
	}
	
	public void select() {
		this.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
	}
	
	public void unselect() {
		this.setBackground(null);
	}
}
