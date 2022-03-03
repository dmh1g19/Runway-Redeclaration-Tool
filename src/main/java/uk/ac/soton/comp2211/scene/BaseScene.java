package uk.ac.soton.comp2211.scene;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.App;

public abstract class BaseScene {
    protected Stage stage;
    protected App app;



    public BaseScene(App app) {
        this.app = app;
    }

    public abstract Scene getScene();




}
