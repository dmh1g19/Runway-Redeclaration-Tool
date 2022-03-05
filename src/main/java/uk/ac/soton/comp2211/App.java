package uk.ac.soton.comp2211;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.scene.*;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    private static int height = 500;
    private static int width = 700;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        stage.setMinWidth(width);
        stage.setMinHeight(height);

        stage.setTitle("Runway Tool");

        defaultScene();
        loadMenu();

        stage.show();
    }

    public void loadSettings() {
        loadScene(new SettingsScene(this));
    }

    public void loadMenu() {
        loadScene(new MenuScene(this));
    }

    public void loadPrimary() {
        loadScene(new PrimaryScene(this));
    }

    public void loadCalculations() {loadScene(new CalculationsScene(this));}

    public void loadScene(BaseScene newScene) {
        newScene.build();
        scene = newScene.setScene();
        stage.setScene(scene);
    }

    public void defaultScene() {
        scene = new Scene(new StackPane(),width,height, Color.BLACK);
        stage.setScene(scene);
    }

    public Scene getScene() {
        return this.scene;
    }


    static void setRoot(String fxml) {
        try {
            scene = new Scene((loadFXML(fxml)),width,height);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}