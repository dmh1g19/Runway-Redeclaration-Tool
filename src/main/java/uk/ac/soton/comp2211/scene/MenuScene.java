package uk.ac.soton.comp2211.scene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.App;

public class MenuScene extends BaseScene {


    public MenuScene(App stage) {
        super(stage);
    }

    @Override
    public Scene getScene() {
        StackPane root = new StackPane();

        Button button = new Button("Hello!!");
        button.setOnMousePressed(e -> app.loadSettings());
        root.getChildren().add(button);

        root.setStyle("-fx-background-color: #81c483");
        Scene scene = new Scene(root, 800, 600);
        return scene;
    }
}
