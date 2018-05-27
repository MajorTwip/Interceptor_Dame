package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.Board;
import ch.ffhs.ftoop.interceptor.dame.beans.Coordinate;
import ch.ffhs.ftoop.interceptor.dame.beans.Stone;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * The visual interface to the game itself
 *
 * @author MajorTwip
 * @version 0.8
 */
class GUIBoard extends GridPane {
	private Backend backend;
	private Board board;
	private GUIStone selectedStone = null;

	/**
	 * Shows the given board
	 *
	 * @param board   Instance of Board to show
	 * @param backend Backend to which to send turns et.al
	 */
	GUIBoard(Board board, Backend backend) {
		super();

		this.board = board;
		this.backend = backend;

		VBox.setVgrow(this, Priority.ALWAYS);

		this.setMinWidth(640);
		this.setMinHeight(640);

	}

	/**
	 * Setup of the Board
	 * BlackWhite and size
	 */
	void draw() {

		for (int x = 0; x <= board.getMaxX(); x++) {
			for (int y = 0; y <= board.getMaxY(); y++) {
				StackPane square = new StackPane();
				Color color;
				if ((x % 2 == 1) ^ (y % 2 == 1)) {
					color = Color.WHITE;
				} else {
					color = Color.BLACK;
				}
				square.setBackground(new Background(new BackgroundFill(color, null, null)));
				GridPane.setColumnIndex(square, x);
				GridPane.setRowIndex(square, y);

				square.setOnMouseEntered(this::mouseOver);
				square.setOnMouseExited(this::mouseLeaved);
				square.setOnMouseClicked(this::mouseClicked);


				this.getChildren().addAll(square);
			}
		}
		if (this.getColumnConstraints().size() * this.getRowConstraints().size() == 0) {
			for (int x = 0; x <= board.getMaxX(); x++) {
				this.getColumnConstraints().add(new ColumnConstraints(20, this.getWidth() / (board.getMaxX() + 1), Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
			}
			for (int y = 0; y <= board.getMaxY(); y++) {
				this.getRowConstraints().add(new RowConstraints(20, this.getHeight() / (board.getMaxY() + 1), Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
			}
		}


		for (Stone stone : board) {
			getStackAt(stone.getCoordinate()).getChildren().add(new GUIStone(this, stone));
		}

	}

	/**
	 * refreshes the stone's locations
	 */
	public void redraw() {
		clearStones();
		for (Stone stone : board) {
			GUIStone guistone = new GUIStone(this, stone);
			getStackAt(stone.getCoordinate()).getChildren().add(guistone);
			guistone.toBack();
		}
	}

	/**
	 * removes every stone from the shown board
	 */
	private void clearStones() {
		for (Node node : this.getChildren()) {
			((StackPane) node).getChildren().removeIf(e -> e.getClass().equals(GUIStone.class));
		}
	}

	/**
	 * Requests apply for choosen turn
	 *
	 * @param e MouseEvent from the clicked Stone
	 */
	private void mouseClicked(MouseEvent e) {
		if (selectedStone != null) {
			Coordinate coord = new Coordinate(GridPane.getColumnIndex((Node) e.getSource()),
					GridPane.getRowIndex((Node) e.getSource()));

			if (backend.getTurnIsLegal(selectedStone.getStone(), coord)) {
				if (backend.applyTurn(selectedStone.getStone(), coord) && backend.isSinglePlayer()) {
					AI.makeTurn(backend);
				}
			}
		}
	}

	/**
	 * Checks if destination for selected stone would be legal. if so, show by green, otherwise by red.
	 *
	 * @param e MouseEvent from the hovered field
	 */
	private void mouseOver(MouseEvent e) {
		if (selectedStone != null) {
			Coordinate coord = new Coordinate(GridPane.getColumnIndex((Node) e.getSource()),
					GridPane.getRowIndex((Node) e.getSource()));

			Color color = Color.RED;
			if (backend.getTurnIsLegal(selectedStone.getStone(), coord)) {
				color = Color.GREEN;
			}
			((StackPane) e.getSource()).setBackground(new Background(new BackgroundFill(color, null, null)));
		}
	}

	/**
	 * returns backgroundcolor back to normal when mouse left
	 *
	 * @param e MouseEvent from the hovered field
	 */
	private void mouseLeaved(MouseEvent e) {
		Color color;
		if ((GridPane.getColumnIndex((Node) e.getSource()) % 2 == 1) ^ (GridPane.getRowIndex((Node) e.getSource()) % 2 == 1)) {
			color = Color.WHITE;
		} else {
			color = Color.BLACK;
		}
		((StackPane) e.getSource()).setBackground(new Background(new BackgroundFill(color, null, null)));
	}

	/**
	 * Select a stone for further checks
	 *
	 * @param stone Selected stone to move
	 */
	void setClickedStone(GUIStone stone) {
		if (!backend.stoneCanBeSelected(stone.getStone())) return;
		if (selectedStone != null) selectedStone.unselect();
		this.selectedStone = stone;
		stone.select();
	}

	/**
	 * Animates disappearing/reappearing of a stone
	 *
	 * @param stone      to make disappear
	 * @param coordinate where to reappear
	 */
	public void animateMove(Stone stone, Coordinate coordinate) {

		GUIStoneAnimation disappearingstone = new GUIStoneAnimation(stone);
		getStackAt(stone.getCoordinate()).getChildren().add(disappearingstone);
		disappearingstone.toFront();
		disappearingstone.disappear();

		GUIStoneAnimation appearingstone = new GUIStoneAnimation(new Stone(coordinate, stone.getIsOwn(), stone.getIsQueen()));
		getStackAt(coordinate).getChildren().add(appearingstone);
		appearingstone.toFront();
		appearingstone.appear();
	}

	/**
	 * Animates disappearing of a stone
	 *
	 * @param stone to make disappear
	 */
	public void animateStoneRemove(Stone stone) {
		GUIStoneAnimation disappearingstone = new GUIStoneAnimation(stone);
		getStackAt(stone.getCoordinate()).getChildren().add(disappearingstone);
		disappearingstone.disappear();
	}


	/**
	 * Helper to get back a field by its coordinates
	 *
	 * @param coord of the requested file
	 * @return StackPane which represents the field
	 */
	private StackPane getStackAt(Coordinate coord) {
		for (Node node : this.getChildren()) {
			if (node.getClass().equals(StackPane.class)) {
				if ((GridPane.getColumnIndex(node) == coord.getX()) && (GridPane.getRowIndex(node) == coord.getY())) {
					return (StackPane) node;
				}
			}
		}
		return null;
	}

}
