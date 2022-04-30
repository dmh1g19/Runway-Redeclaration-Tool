package uk.ac.soton.comp2211.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.junit.jupiter.api.Test;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.controllers.SelectionController;
import uk.ac.soton.comp2211.scene.BaseScene;

import java.util.Objects;

public class SelectionView extends BaseView {

    //View Nodes
    ComboBox<Airport> airportSelectionList = new ComboBox<>();
    Button selectButton = new Button("Select");
    Button backButton = new Button("Back");


    public SelectionView() {
        view = createView();
    }

    public ComboBox<Airport> getAirportSelectionList() {
        return airportSelectionList;
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public Parent createView() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();
        vbox.getStyleClass().add("selectArea");

        vbox.getChildren().add(createTitle());
        vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
        vbox.getChildren().add(createSelection());

        gridPane.add(vbox, 1, 1);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        createBackButton();
        borderPane.setLeft(backButton);

        return borderPane;
    }

    private Node createTitle() {
        Label titleLabel = new Label("Select Airport");
        titleLabel.getStyleClass().add("selectLabel");
        return titleLabel;
    }

    private Node createSelection() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getStyleClass().add("selectBox");
        hbox.setPrefWidth(400);

        airportSelectionList.setMaxWidth(Double.MAX_VALUE);
        airportSelectionList.getStyleClass().add("airportList");

        selectButton.getStyleClass().add("select");

        hbox.getChildren().addAll(airportSelectionList,selectButton);
        HBox.setHgrow(airportSelectionList, Priority.ALWAYS);

        return hbox;
    }
    public Button getBackButton(){
        return backButton;
    }

    public Node createBackButton() {
        BorderPane.setMargin(backButton, new Insets(10,10,10,10));

        return backButton;
    }



}
