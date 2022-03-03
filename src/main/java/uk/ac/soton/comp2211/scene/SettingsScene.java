package uk.ac.soton.comp2211.scene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.App;

public class SettingsScene extends BaseScene {

    public SettingsScene(App stage) {
        super(stage);
    }

    @Override
    public Scene getScene() {
        StackPane root = new StackPane();

        Button button = new Button("This is settings!");
        button.setOnMousePressed(e -> app.loadMenu());
        root.getChildren().add(button);

        Scene scene = new Scene(root, 800, 600);
        return scene;
    }
}
