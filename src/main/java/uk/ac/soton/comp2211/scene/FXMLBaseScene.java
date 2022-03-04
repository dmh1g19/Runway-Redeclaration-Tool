package uk.ac.soton.comp2211.scene;

import javafx.fxml.FXMLLoader;
import uk.ac.soton.comp2211.App;

import java.io.IOException;

public abstract class FXMLBaseScene extends BaseScene {

    public FXMLBaseScene(App app) {
        super(app);
    }

    public void linkToFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(this);
        root = fxmlLoader.load();
    }

}
