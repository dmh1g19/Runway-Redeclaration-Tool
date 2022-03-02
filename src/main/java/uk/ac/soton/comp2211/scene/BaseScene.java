package uk.ac.soton.comp2211.scene;

import javafx.scene.Scene;
import uk.ac.soton.comp2211.ui.AppWindow;

public class BaseScene {
    protected final AppWindow appWindow;

    protected Scene scene;

    public BaseScene (AppWindow appWindow) {
        this.appWindow = appWindow;
    }


}
