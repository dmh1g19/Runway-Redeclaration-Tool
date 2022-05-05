package uk.ac.soton.comp2211.views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.components.SideOnRunway;
import uk.ac.soton.comp2211.controllers.NotificationsController;
import uk.ac.soton.comp2211.controllers.ViewsController;


public class MenuView extends BaseView {

    //View Nodes
    private Text calcButton = new Text("Calculate");
    private Text settingsButton = new Text("Settings");
    private Text exitButton = new Text("Exit");
    private Label airportIndicator = new Label();
    private Button notifButton = new Button("View Notifications");

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
    public Button getNotifButton() {
        return notifButton;
    }

    public Parent createView() {
        //Airport Indicator
        airportIndicator.getStyleClass().add("airportIndicator");
        BorderPane layout = new BorderPane();

        BorderPane.setAlignment(airportIndicator, Pos.TOP_RIGHT);

        layout.setCenter(createMenu());
        layout.setTop(createTopBar());

        return layout;
    }

    public Node createTopBar() {
        HBox hbox = new HBox();


        BorderPane.setMargin(hbox, new Insets(8,8,4,8));

        airportIndicator.setAlignment(Pos.TOP_RIGHT);
        airportIndicator.getStyleClass().add("airportIndicator");

        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        hbox.setSpacing(12);
        hbox.getChildren().addAll(spacing, notifButton,airportIndicator);

        return hbox;
    }


    private Node createMenu() {
        VBox menuItems = new VBox();
        menuItems.setAlignment(Pos.CENTER);
        menuItems.getStyleClass().add("menu");

        calcButton.getStyleClass().add("menuItem");


        settingsButton.getStyleClass().add("menuItem");

        //Exit
        exitButton.getStyleClass().add("menuItem");

        //Add to Menu Vbox
        menuItems.getChildren().addAll(calcButton, settingsButton, exitButton);

        return new Group(menuItems);
    }

}
