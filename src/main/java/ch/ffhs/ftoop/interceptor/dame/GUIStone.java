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

/**
 * Visual representation of a Stone
 * @author MajorTwip
 *
 */
public class GUIStone extends StackPane{
	GUIBoard board;
	Stone stone;
	Circle circle;
	

	/**
	 * Creates a new StackPane and the Circle which represents the stone 
	 * @param board where it will be placed on, callback
	 * @param stone to visualise
	 */
	GUIStone(GUIBoard board, Stone stone){
		super();
		this.board=board;
		this.stone = stone;
		GridPane.setHgrow(this, Priority.ALWAYS);
		GridPane.setVgrow(this, Priority.ALWAYS);
		
		//Add Circle
		
		circle = new Circle();
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
		
		//board.add(this, stone.getCoordinate().getX(), stone.getCoordinate().getY());
		
	}
	
	
	/**
	 * show that this stone is selected by changing background to blue
	 */
	public void select() {
		this.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
	}
	
	/**
	 * show that this stone is  NOT selected by removing blue background
	 */
	public void unselect() {
		this.setBackground(null);
	}
	
	/**
	 * Gives back Stone which is represented
	 * @return Stone 
	 */
	public Stone getStone() {
		return stone;
	}

}
