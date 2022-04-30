package uk.ac.soton.comp2211.scene;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.util.StringConverter;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.PhysicalRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.utility.XMLUtil;

import javax.swing.text.html.Option;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class SelectionScene extends BaseScene {

    ComboBox<Airport> airportList;

    public SelectionScene(App app) {
        super(app);
    }

    @Override
    public void build() {
        System.out.println("building selection scene");
        root = new StackPane();

        //Drop Down List
        airportList = new ComboBox<>();
        ObservableList<Airport> airports = FXCollections.observableArrayList(app.getAirports());
        airportList.setItems(airports);
        airportList.getSelectionModel().selectFirst();
        if (!Objects.isNull(app.getSelectedAirport())) {
            airportList.getSelectionModel().select(app.getSelectedAirport());
        }
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

        //Select Button
        Button select = new Button("Select");
        select.setOnAction(e -> {
            app.setSelectedAirport(airportList.getValue());
            app.loadMenu();
        });
        select.getStyleClass().add("select");

        // Selection Airport and Button
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(airportList,select);
        hbox.getStyleClass().add("selectBox");
        hbox.setPrefWidth(400);

        HBox.setHgrow(airportList, Priority.ALWAYS);

        //Select Airport
        Label label = new Label("Select Airport");
        label.getStyleClass().add("selectLabel");

        //Separator Line
        Separator line = new Separator(Orientation.HORIZONTAL);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label,line,hbox);
        vbox.getStyleClass().add("selectArea");

        GridPane gridPane = new GridPane();
        gridPane.add(vbox,1,1);
        gridPane.setAlignment(Pos.CENTER);

        root.getChildren().add(gridPane);
    }


}
