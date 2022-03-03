package uk.ac.soton.comp2211;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.scene.MenuScene;
import uk.ac.soton.comp2211.scene.SettingsScene;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        MenuScene scene = new MenuScene(this);
        stage.setScene(scene.getScene());

        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public void loadSettings() {
        SettingsScene scene = new SettingsScene(this);
        stage.setScene(scene.getScene());
    }

    public void loadMenu() {
        MenuScene scene = new MenuScene(this);
        stage.setScene(scene.getScene());
    }


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}