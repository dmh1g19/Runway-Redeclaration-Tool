package org.openjfx.ui;

import javafx.stage.Stage;

public class AppWindow {

    private final int width;
    private final int height;

    private final Stage stage;

    public AppWindow(Stage stage, int width, int height) {
        this.width = width;
        this.height = height;
        this.stage = stage;
    }

}
