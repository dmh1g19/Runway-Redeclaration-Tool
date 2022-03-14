package uk.ac.soton.comp2211.scene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.App;

public class SettingsScene extends BaseScene {

    public SettingsScene(App stage) {
        super(stage);
    }

    public void build() {
        root = new StackPane();

        //Empty scene so far
        Button button = new Button("This is settings!");
        button.setOnMousePressed(e -> app.loadMenu());
        root.getChildren().add(button);


    }
}
