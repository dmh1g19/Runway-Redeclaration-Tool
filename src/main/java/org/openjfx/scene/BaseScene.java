package org.openjfx.scene;

import javafx.scene.Scene;
import org.openjfx.ui.AppWindow;

public class BaseScene {
    protected final AppWindow appWindow;

    protected Scene scene;

    public BaseScene (AppWindow appWindow) {
        this.appWindow = appWindow;
    }


}
