package uk.ac.soton.comp2211.scene;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.App;

public abstract class BaseScene {
    protected Stage stage;
    protected App app;
    protected Scene scene;
    protected Pane root;

    public BaseScene(App app) {
        this.app = app;
    }

    public BaseScene() {}

    public Scene getScene() {
        return this.scene;
    }

    public Scene setScene() {
        var previous = app.getScene();
        scene = new Scene(root,previous.getWidth(),previous.getHeight());
        scene.getStylesheets().add(App.class.getResource("main.css").toExternalForm());
        return scene;
    }

    public abstract void build();


}
