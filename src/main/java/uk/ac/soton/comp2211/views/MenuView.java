package uk.ac.soton.comp2211.views;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;

public class MenuView extends BaseView {

    //View Nodes
    Text calcButton = new Text("Calculate");
    Text settingsButton = new Text("Settings");
    Text exitButton = new Text("Exit");
    Label airportIndicator = new Label();

    public MenuView() {
        view = createView();
    }

    public Label getAirportIndicator() {
        return airportIndicator;
    }

    public Text getCalcButton() {
        return calcButton;
    }

    public Text getSettingsButton() {
        return settingsButton;
    }

    public Text getExitButton() {
        return exitButton;
    }

    public Parent createView() {
        //Airport Indicator
        airportIndicator.getStyleClass().add("airportIndicator");

        BorderPane layout = new BorderPane();

        layout.setCenter(createMenu());
        layout.setTop(airportIndicator);

        return layout;
    }

    private Node createMenu() {
        VBox menuItems = new VBox();
        menuItems.setAlignment(Pos.CENTER);
        menuItems.getStyleClass().add("menu");

        //calcButton.setOnMouseClicked(e -> app.loadCalculations());
        calcButton.getStyleClass().add("menuItem");

        //Settings
        //settingsButton.setOnMouseClicked(e -> app.loadSettings());
        settingsButton.getStyleClass().add("menuItem");

        //Exit
        exitButton.getStyleClass().add("menuItem");

        //Add to Menu Vbox
        menuItems.getChildren().addAll(calcButton, settingsButton, exitButton);

        return new Group(menuItems);
    }

}
