package uk.ac.soton.comp2211.scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.util.StringConverter;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.Runway;

import java.util.ArrayList;

public class SelectionScene extends BaseScene {


    public SelectionScene(App app) {
        super(app);
    }

    @Override
    public void build() {
        root = new StackPane();


        Airport airport = new Airport("Heathrow",null);
        Airport newAirport = new Airport("Stansted", null);
        Runway runway = new Runway("Runway01",4500,50,18,3902,3902,3902,3595, new Pair<>(0,0));
        Runway[] runs = {runway};
        airport.setRunways(runs);

        ObservableList<Airport> airports = FXCollections.observableArrayList();
        airports.addAll(airport,newAirport);

        ComboBox<Airport> airportList = new ComboBox<>();
        airportList.setItems(airports);
        airportList.getSelectionModel().selectFirst();
        airportList.setEditable(false);
        airportList.getStyleClass().add("airportList");
        airportList.setMaxWidth(Double.MAX_VALUE);

        airportList.setConverter(new StringConverter<Airport>() {
            @Override
            public String toString(Airport airport) {
                return airport.getName();
            }

            @Override
            public Airport fromString(String s) {
                return null;
            }
        });

        Button select = new Button("Select");
        select.setOnAction(e -> {
            app.setAirport(airportList.getValue());
            app.loadMenu();
        });
        select.getStyleClass().add("select");



        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(airportList,select);
        hbox.getStyleClass().add("selection");
        hbox.setPrefWidth(400);

        HBox.setHgrow(airportList, Priority.ALWAYS);

        Label label = new Label("Select Airport");
        label.getStyleClass().add("selectLabel");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label,hbox);
        vbox.getStyleClass().add("selection");

        GridPane gridPane = new GridPane();
        gridPane.add(vbox,1,1);
        gridPane.setAlignment(Pos.CENTER);

        root.getChildren().add(gridPane);

    }
}
