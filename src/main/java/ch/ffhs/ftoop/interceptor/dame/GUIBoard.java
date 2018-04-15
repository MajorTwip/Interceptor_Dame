package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GUIBoard extends GridPane{
	Board board;
	
	public GUIBoard(Board board) {
		super();
		
		this.board = board;
		
		Button btn = new Button();
        btn.setText("Go to menu");	
        this.getChildren().add(btn);
        
        
        Long squareSize = 100L;
        
        for(int x=0; x<=board.getMaxX();x++) {
        	for(int y=0; y<=board.getMaxY();y++) {
        		Rectangle square = new Rectangle();
        		square.setWidth(squareSize);
        		square.setHeight(squareSize);
        		if((x%2==1)^(y%2==1)) {
        			square.setFill(Color.WHITE);
        			System.out.println("Grey");
        		}else {
        			square.setFill(Color.BLACK);
        			System.out.println("Blue");
        		}
        		GridPane.setColumnIndex(square, x+1);
        		GridPane.setRowIndex(square, y);
        		this.getChildren().addAll(square);
        	}
        }
	}
}
