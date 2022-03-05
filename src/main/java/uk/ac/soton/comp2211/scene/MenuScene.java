package uk.ac.soton.comp2211.scene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.App;

import java.io.IOException;

public class MenuScene extends BaseScene {


    public MenuScene(App stage) {
        super(stage);
    }


    public void build() {
        root = new StackPane();


        Button button = new Button("Calculate");
        button.setOnMousePressed(e ->app.loadCalculations());
        root.getChildren().add(button);

        root.setStyle("-fx-background-color: #81c483");
    }

}
