package uk.ac.soton.comp2211.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.scene.FXMLBaseScene;

import java.io.IOException;

public class PrimaryScene extends FXMLBaseScene {
    @FXML
    private Button primaryButton;

    public PrimaryScene(App app) {
        super(app);
    }


    @Override
    public void build() {
        try {
            linkToFXML("primary");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @FXML
    private void initialize() {
        primaryButton.setOnAction(e -> app.loadMenu());
    }


}
