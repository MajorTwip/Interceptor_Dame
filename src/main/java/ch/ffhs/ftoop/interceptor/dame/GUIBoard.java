package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GUIBoard extends GridPane{
	Backend backend;
	Board board;
	Boolean isLandscape = true;
	GUIStone selectedStone = null;
	
	
	
	public GUIBoard(Board board, Backend backend) {
		super();
		
		this.board = board;
		this.backend = backend;
		
		VBox.setVgrow(this, Priority.ALWAYS);
		
		this.setMinWidth(640);
		this.setMinHeight(640);        

	}
	public void draw() {
               
        for(int x=0; x<=board.getMaxX();x++) {
        	for(int y=0; y<=board.getMaxY();y++) {
        		StackPane square = new StackPane();
        		Color color;
        		if((x%2==1)^(y%2==1)) {
        			color = Color.WHITE;
        		}else {
        			color = Color.BLACK;
        		}
        		square.setBackground(new Background(new BackgroundFill(color,null,null)));
        		GridPane.setColumnIndex(square, x);
        		GridPane.setRowIndex(square, y);
        		
        		square.setOnMouseEntered(e->mouseOver(e));
        		square.setOnMouseExited(e->mouseLeaved(e));
        		square.setOnMouseClicked(e->mouseClicked(e));
        		
        		
        		this.getChildren().addAll(square);
        	}
        }
        for(int x=0; x<=board.getMaxX();x++) {
        	this.getColumnConstraints().add(new ColumnConstraints(20, this.getWidth()/(board.getMaxX()+1), Double.POSITIVE_INFINITY,Priority.ALWAYS,HPos.CENTER,true));
        }
    	for(int y=0; y<=board.getMaxY();y++) {
        	this.getRowConstraints().add(new RowConstraints(20, this.getHeight()/(board.getMaxY()+1), Double.POSITIVE_INFINITY,Priority.ALWAYS,VPos.CENTER,true));
    	}
    	
    	for(Stone stone:board) {
    		new GUIStone(this, stone);
    	}

	}
	private void mouseClicked(MouseEvent e) {
		if(selectedStone!=null) {
			Coordinate coord = new Coordinate(GridPane.getColumnIndex((Node) e.getSource()),
				GridPane.getRowIndex((Node)e.getSource()));
	
		if(backend.getTurnIsLegal(selectedStone.getStone(), coord)){
			backend.applyTurn(selectedStone.getStone(), coord);
		}
	}
}
	private void mouseOver(MouseEvent e) {
		if(selectedStone!=null) {
			Coordinate coord = new Coordinate(GridPane.getColumnIndex((Node) e.getSource()),
					GridPane.getRowIndex((Node)e.getSource()));
			
			Color color = Color.RED;
			if(backend.getTurnIsLegal(selectedStone.getStone(), coord)){
				color = Color.GREEN;
			}
			((StackPane)e.getSource()).setBackground(new Background(new BackgroundFill(color,null,null)));
		}
	}
	
	private void mouseLeaved(MouseEvent e) {
		Color color;
		if((GridPane.getColumnIndex((Node) e.getSource())%2==1)^(GridPane.getRowIndex((Node)e.getSource())%2==1)) {
			color = Color.WHITE;
		}else {
			color = Color.BLACK;
		}
		((StackPane)e.getSource()).setBackground(new Background(new BackgroundFill(color,null,null)));
	}
	
	
	public void setClickedStone(GUIStone stone) {
		if(selectedStone!=null)selectedStone.unselect();
		this.selectedStone = stone;
		stone.select();		
	}
	
}
