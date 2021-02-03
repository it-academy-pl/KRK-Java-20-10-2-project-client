package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;

class VisualElements {
    private TextField xNameValue;
    private TextField xPasswordValue;
    private TextField oNameValue;
    private TextField oPasswordValue;

    private Text error;
    private Text status;
    private Button[] buttons;

    private final GameController controller = new GameController(this);

    Group group() {
        error = text("", Color.RED);
        status = text("Game Status");

        Text xName = text("X-name:");
        xNameValue = new TextField();
        Text xPassword = text("X-password:");
        xPasswordValue = new TextField();
        Button registerXPlayer = new Button("Register-X");
        registerXPlayer.setOnMouseClicked(click -> controller.registerXPlayer());

        Text oName = text("O-name:");
        oNameValue = new TextField();
        Text oPassword = text("O-password:");
        oPasswordValue = new TextField();
        Button registerOPlayer = new Button("Register-O");
        registerOPlayer.setOnMouseClicked(click -> controller.registerOPlayer());

        Button createGame = new Button("Create Game");
        createGame.setOnMouseClicked(click -> controller.createGame());

        Button joinGame = new Button("Join Game");
        joinGame.setOnMouseClicked(click -> controller.joinGame());

        GridPane players = grid();

        players.addRow(0, xName, xNameValue, registerXPlayer);
        players.addRow(1, xPassword, xPasswordValue, createGame);
        players.addRow(2, oName, oNameValue, registerOPlayer);
        players.addRow(3, oPassword, oPasswordValue, joinGame);

        buttons = new Button[9];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(" ");
            final int index = i;
            buttons[i].setOnMouseClicked(click -> controller.makeMove(index));
        }

        GridPane field = grid();

        field.addRow(0, buttons[0], buttons[1], buttons[2]);
        field.addRow(1, buttons[3], buttons[4], buttons[5]);
        field.addRow(2, buttons[6], buttons[7], buttons[8]);

        GridPane mainGrid = grid();
        mainGrid.addColumn(0, players, status, field, error);

        return new Group(mainGrid);
    }

    String getXName() {
        return xNameValue.getCharacters().toString();
    }

    String getOName() {
        return oNameValue.getCharacters().toString();
    }

    String getXPassword() {
        return xPasswordValue.getCharacters().toString();
    }

    String getOPassword() {
        return oPasswordValue.getCharacters().toString();
    }

    Text getError() {
        return error;
    }

    Text getStatus() {
        return status;
    }

    Button[] getButtons() {
        return buttons;
    }

    private static GridPane grid() {
        GridPane grid = new GridPane();
        grid.setMinSize(500, 120);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    private static Text text(String text) {
        return text(text, Color.BLACK);
    }

    private static Text text(String text, Paint color) {
        Text t = new Text(text);
        t.setFont(new Font(24));
        t.setFill(color);
        return t;
    }

}
