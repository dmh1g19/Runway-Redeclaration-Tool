package uk.ac.soton.comp2211.scene;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.App;

import java.io.IOException;

public class MenuScene extends BaseScene {


    public MenuScene(App stage) {
        super(stage);
    }


    public void build() {
        root = new StackPane();

        //Calculate
        Text calcButton = new Text("Calculate");
        calcButton.setOnMouseClicked(e -> app.loadCalculations());
        calcButton.getStyleClass().add("menuItem");

        //Settings
        Text settingsButton = new Text("Settings");
        settingsButton.setOnMouseClicked(e -> app.loadSettings());
        settingsButton.getStyleClass().add("menuItem");

        //Exit
        Text exitButton = new Text("Exit");
        exitButton.setOnMouseClicked(e -> Platform.exit());
        exitButton.getStyleClass().add("menuItem");

        //Menu Items
        VBox menuItems = new VBox();
        menuItems.setAlignment(Pos.CENTER);
        menuItems.getChildren().addAll(calcButton,settingsButton,exitButton);
        menuItems.getStyleClass().add("menu");

        //Airport Indicator
        Label airportIndicator = new Label("Airport: " + app.getSelectedAirport().getName());
        airportIndicator.getStyleClass().add("airportIndicator");
        airportIndicator.setOnMouseClicked(e -> app.loadSelection());

        BorderPane layout = new BorderPane();
        layout.setCenter(new Group(menuItems));
        layout.setTop(airportIndicator);



        root.getChildren().add(layout);
    }

}
