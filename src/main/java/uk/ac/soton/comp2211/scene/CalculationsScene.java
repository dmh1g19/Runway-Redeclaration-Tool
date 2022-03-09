package uk.ac.soton.comp2211.scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;
import javafx.util.StringConverter;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.components.SideOnRunway;
import uk.ac.soton.comp2211.components.TopDownRunway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CalculationsScene extends BaseScene {

    public CalculationsScene(App stage) {
        super(stage);
    }

    @Override
    public void build() {
        root = new AnchorPane();

        Runway runwa = new Runway("Runway01", 4500, 50, 18, 3902, 3902, 3902, 3595, 5);
        Runway runwa1 = new Runway("Runway02", 4500, 50, 18, 3902, 3902, 3902, 3595, 5);

        ArrayList<Runway> runways = new ArrayList<>();
        runways.add(runwa);
        runways.add(runwa1);

        PhysicalRunway prun = new PhysicalRunway(runways);

        ObservableList<Runway> runways1 = FXCollections.observableArrayList();
        runways1.addAll(prun.getRunways());
        System.out.println(prun.getRunways());
        /**
         * GATHERING INPUT
         */
        GridPane inputForm = new GridPane();
        inputForm.getStyleClass().add("inputs");

        //Title
        Label headerLabel = new Label("Calculations");
        headerLabel.getStyleClass().add("headerCalc");

        Separator line = new Separator();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(headerLabel,line);
        inputForm.add(vbox,0,0,2,1);

        //Runway
        Label runwayLabel = new Label("Runway THR: ");
        inputForm.add(runwayLabel, 0, 1);

        ComboBox<Runway> runwaySelect = new ComboBox<Runway>();
        runwaySelect.setItems(runways1);
        runwaySelect.getSelectionModel().selectFirst();
        runwaySelect.setEditable(false);
        runwaySelect.setConverter(new StringConverter<Runway>() {
            @Override
            public String toString(Runway runway) {
                return runway.getName();
            }

            @Override
            public Runway fromString(String s) {
                return null;
            }
        });
        inputForm.add(runwaySelect,1,1);

        //Obstacle
        Label obstacleLabel = new Label("Obstacle: ");
        inputForm.add(obstacleLabel, 0,2);

        ComboBox<Obstacle> obstacleSelect = new ComboBox<>();
        obstacleSelect.setEditable(false);
        obstacleSelect.setConverter(new StringConverter<Obstacle>() {
            @Override
            public String toString(Obstacle obstacle) {
                return "Obstacle";
            }

            @Override
            public Obstacle fromString(String s) {
                return null;
            }
        });
        inputForm.add(obstacleSelect,1,2);

        //DISTANCE
        Label distanceLabel = new Label("Distance to THR: ");
        inputForm.add(distanceLabel, 0,3);

        TextField distanceInput = new TextField();
        inputForm.add(distanceInput, 1,3);

        //TAKE OFF AWAY/OVER
        Label directionLabel = new Label("Direction: ");
        inputForm.add(directionLabel, 0,4);

        ObservableList<Boolean> direction = FXCollections.observableArrayList();
        direction.addAll(true,false);

        ComboBox<Boolean> directionSelect = new ComboBox<>();
        directionSelect.setItems(direction);
        directionSelect.getSelectionModel().selectFirst();
        directionSelect.setEditable(false);
        directionSelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(Boolean aBoolean) {
                if (aBoolean) return "Towards";
                else return "Away/Over";
            }

            @Override
            public Boolean fromString(String s) {
                return null;
            }
        });
        inputForm.add(directionSelect, 1,4);

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> app.loadMenu());
        inputForm.add(calculateButton,0,5,2,1);
        calculateButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setFillWidth(calculateButton,true);


        AnchorPane.setLeftAnchor(inputForm,0d);
        AnchorPane.setTopAnchor(inputForm,0d);


        //test values

        Button sideOn = new Button("Side");
        sideOn.setOnAction(e -> app.loadSide());
        AnchorPane.setRightAnchor(sideOn, 0d);
        AnchorPane.setTopAnchor(sideOn, 0d);


        Button s1 = new Button("Top");
        s1.setOnAction(e -> app.loadTopDown());
        AnchorPane.setRightAnchor(s1, 0d);
        AnchorPane.setBottomAnchor(s1, 0d);

        root.getChildren().addAll(sideOn, s1, inputForm);

        root.setStyle("-fx-background-color: #81c483");
    }
}
