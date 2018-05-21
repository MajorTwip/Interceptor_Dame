package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Stone;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GUIStoneAnimation extends StackPane{
	Stone stone;
	Circle circle;
	Text queen = null;
	

	
	GUIStoneAnimation(Stone stone){
		super();
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
			queen = new Text("Q");
			queen.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
			queen.setFill(Color.WHITE);
			queen.setStroke(Color.BLACK);
			this.getChildren().add(queen);
		}		
	}

	public void disappear(){
		ImageView portal = new ImageView();
        portal.setImage(new Image("/portal.gif"));
        portal.fitHeightProperty().bind(((StackPane)this.getParent()).heightProperty());
        portal.fitWidthProperty().bind(((StackPane)this.getParent()).widthProperty());
		this.getChildren().add(portal);
		portal.toBack();

		ScaleTransition scaleTransition = new ScaleTransition();
		scaleTransition.setDuration(Duration.millis(2000));
		scaleTransition.setNode(circle);
		scaleTransition.setToX(0);
		scaleTransition.setToY(0);
		scaleTransition.play();
		scaleTransition.setOnFinished(e->((StackPane)this.getParent()).getChildren().remove(this));
	}
	
	public void appear(){
		circle.setScaleX(0);
		circle.setScaleY(0);
		
		ImageView portal = new ImageView();
        portal.setImage(new Image("/portal.gif"));
        portal.fitHeightProperty().bind(((StackPane)this.getParent()).heightProperty());
        portal.fitWidthProperty().bind(((StackPane)this.getParent()).widthProperty());
		this.getChildren().add(portal);
		portal.toBack();

		ScaleTransition scaleTransition = new ScaleTransition();
		scaleTransition.setDuration(Duration.millis(2000));
		scaleTransition.setDelay(Duration.millis(1500));
		scaleTransition.setNode(circle);
		scaleTransition.setFromX(0);
		scaleTransition.setFromY(0);
		scaleTransition.setToX(1);
		scaleTransition.setToY(1);
		scaleTransition.play();
		scaleTransition.setOnFinished(e->((StackPane)this.getParent()).getChildren().remove(this));
	}
}
