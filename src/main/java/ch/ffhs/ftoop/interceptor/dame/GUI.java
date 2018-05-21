package ch.ffhs.ftoop.interceptor.dame;

import ch.ffhs.ftoop.interceptor.dame.beans.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class GUI implements DameGUIInterface {

    private Backend backend;
    private Stage stage;
    private ResourceBundle texts;
    GUIBoard guiboard=null;

    GUI(Stage stage) {
        this.stage = stage;
        texts = ResourceBundle.getBundle("gui_texts", new Locale("EN"));


        stage.setTitle(texts.getString("title"));
        //stage.setMaximized(true);
        //stage.setMinWidth(640);
        //stage.setMinHeight(640);
    }

    private MenuBar getMenu() {
        MenuBar menubar = new MenuBar();

        Menu menuGame = new Menu("Game");

        MenuItem menuItemNewGame = new MenuItem("New Game");
        menuItemNewGame.setOnAction(e -> backend.startNewGame(GameMode.Singleplayer8X8));

        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(e -> backend.quitGame());

        menuGame.getItems().addAll(menuItemNewGame, menuItemQuit);

        menubar.getMenus().addAll(menuGame);

        return menubar;
    }

    void setBackend(Backend backend) {
        this.backend = backend;
    }


    @Override
    public void showMenu() {
        stage.setScene(new Scene(new GUIMenu(backend)));
        stage.show();
    }

    @Override
    public void showBoard(Board board) {
        Scene scene = new Scene(new VBox());
        ((VBox) scene.getRoot()).getChildren().add(getMenu());

        guiboard = new GUIBoard(board, backend);
        ((VBox) scene.getRoot()).getChildren().add(guiboard);

        stage.setScene(scene);
        stage.show();
        guiboard.draw();
    }

    @Override
    public void closeApplication() {
        backend.quitGame();
    }

    @Override
    public void initiateTurn() {

    }

    //TODO maybe additional argument: String array with additional text patterns
    // which are displayed in the messagebox (e.g. replace %1 in message with element[0] in String array)
    @Override
    public boolean showMessageBox(MessageBox msgbox) {
        Alert alert;
        switch (msgbox.getType()) {
            case OK:
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(msgbox.getTitle());
                alert.setContentText(msgbox.getMessage());
                if (!msgbox.getHeader().equals("")) alert.setHeaderText(msgbox.getHeader());
                break;
            case LOSE:
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(texts.getString("msg_lost_title"));
                alert.setContentText(texts.getString("msg_lost_content"));
                break;
            case VICTORY:
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(texts.getString("msg_win_title"));
                alert.setContentText(texts.getString("msg_win_content"));
                break;
            case YESNO:
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle(msgbox.getTitle());
                alert.setContentText(msgbox.getMessage());
                if (!msgbox.getHeader().equals("")) alert.setHeaderText(msgbox.getHeader());
                break;
            default:
                alert = new Alert(AlertType.INFORMATION);
                break;
        }

        Optional<ButtonType> response = alert.showAndWait();
        return response.isPresent() && response.get() == ButtonType.OK;
    }

    @Override
    public void animateMove(Stone stone, Coordinate coordinate) {
        if(guiboard!=null) {
        	guiboard.animateMove(stone, coordinate);
        }
    }

    @Override
    public void animateStoneRemove(Stone stone) {
    	 if(guiboard!=null) {
         	guiboard.animateStoneRemove(stone);
         }
    }

    void redraw() {
    	guiboard.redraw();
    }

}
