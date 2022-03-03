package uk.ac.soton.comp2211;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController {
    public Button primaryButton;
    public Button secondaryButton;
    private Scene scene;
    private static App app;

    public ScreenController(Scene scene, App app) {
        this.scene = scene;
        this.app = app;
    }

    @FXML
    protected void loadPrimary() throws IOException {
        App.setRoot("secondary" );
    }

    @FXML
    protected void loadMenu() {
        app.loadMenu();
    }

}
