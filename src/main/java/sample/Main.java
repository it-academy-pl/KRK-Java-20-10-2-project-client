package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        VisualElements visualElements = new VisualElements();

        Group root = visualElements.group();
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
