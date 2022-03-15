package uk.ac.soton.comp2211.scene;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.components.PredefinedObstacles;
import uk.ac.soton.comp2211.listeners.RunwayUpdatedListener;
import uk.ac.soton.comp2211.utility.Calculator;

import java.util.*;

public class CalculationsScene extends BaseScene {
    private Runway currentRunway;
    private ArrayList<RunwayUpdatedListener> runwayUpdatedListeners = new ArrayList<>();
    private ComboBox<Obstacle> obstacleSelect;
    private ComboBox<PhysicalRunway> runwaySelect;
    private ComboBox<Boolean> directionSelect;
    private TextField distanceFirstInput;
    private TextField distanceSecondInput;
    private Label distanceFirstLabel;
    private Label distanceSecondLabel;
    private ComboBox<Runway> thresholdSelect;

    public CalculationsScene(App stage) {
        super(stage);
    }

    @Override
    public void build() {
        root = new AnchorPane();

        Runway runwa = new Runway("Runway01", 4500, 50, 18, 3902, 3902, 3902, 3595, 5);
        Runway runwa1 = new Runway("Runway02", 4500, 50, 18, 3902, 3902, 3902, 3595, 5);
        //setting default current runway values provided to views if they are opened without first doing a calculation
        //TODO remove these and replace them with error handeling so you cant expand views without first defining a runway
        currentRunway = runwa;
        currentRunway.setObstacle( new ObstacleOnRunway("test", 12, 0, 3646, 0,5));
        try{
            currentRunway = Calculator.TowardsObstacle(currentRunway);
        }catch(Exception ignored) {}



        ArrayList<Runway> runways = new ArrayList<>();
        runways.add(runwa);
        runways.add(runwa1);

        PhysicalRunway prun = new PhysicalRunway(runwa,runwa1);

        ObservableList<Runway> runways1 = FXCollections.observableArrayList();
        runways1.addAll(prun.getRunways());
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

        //Physical Runway
        Label runwayLabel = new Label("Select Runway:");
        inputForm.add(runwayLabel, 0, 1);

        runwaySelect = new ComboBox<>();
        runwaySelect.setItems(FXCollections.observableArrayList
                (app.getSelectedAirport().getRunways()));
        runwaySelect.getSelectionModel().selectFirst();
        runwaySelect.setEditable(false);
        runwaySelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(PhysicalRunway runway) {
                return runway.getName();
            }

            @Override
            public PhysicalRunway fromString(String s) {
                return null;
            }
        });
        runwaySelect.valueProperty().addListener((observableValue, oldR, newR) -> Platform.runLater(() -> {
            distanceFirstLabel.setText("Distance to " + newR.getFirst().getName() + ":");
            distanceSecondLabel.setText("Distance to " + newR.getSecond().getName() + ":");
            distanceFirstInput.setText("");
            distanceSecondInput.setText("");
            thresholdSelect.setItems(FXCollections.observableArrayList(newR.getRunways()));
            thresholdSelect.getSelectionModel().selectFirst();
        }));
        inputForm.add(runwaySelect,1,1);

        //Obstacle
        Label obstacleLabel = new Label("Obstacle: ");
        inputForm.add(obstacleLabel, 0,2);


        obstacleSelect = new ComboBox<>();
        obstacleSelect.setItems(PredefinedObstacles.getObstacles());
        obstacleSelect.getSelectionModel().selectFirst();
        obstacleSelect.setEditable(false);
        obstacleSelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(Obstacle obstacle) {
                return obstacle.getName();
            }

            @Override
            public Obstacle fromString(String s) {
                return null;
            }
        });

        inputForm.add(obstacleSelect,1,2);

        //DISTANCE FIRST
        distanceFirstLabel = new Label("Distance to THR: ");
        inputForm.add(distanceFirstLabel, 0,3);

        distanceFirstInput = new TextField();
        distanceFirstInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                distanceFirstInput.setText(oldValue);
            }
        });
        distanceFirstInput.setAlignment(Pos.CENTER_RIGHT);

        Text firstMetre = new Text("m");
        firstMetre.getStyleClass().add("metre");
        HBox fhbox = new HBox();
        fhbox.getChildren().addAll(distanceFirstInput, firstMetre);
        inputForm.add(fhbox, 1,3, 1, 1);

        //DISTANCE SECOND
        distanceSecondLabel = new Label("Distance to THR: ");
        inputForm.add(distanceSecondLabel, 0,4);

        distanceSecondInput = new TextField();
        distanceSecondInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                distanceSecondInput.setText(oldValue);
            }
        });
        distanceSecondInput.setAlignment(Pos.CENTER_RIGHT);

        Text secondMetre = new Text("m");
        secondMetre.getStyleClass().add("metre");
        HBox shbox = new HBox();
        shbox.getChildren().addAll(distanceSecondInput, secondMetre);
        inputForm.add(shbox, 1,4, 1, 1);

        //TAKE OFF AWAY/OVER
        Label directionLabel = new Label("Direction: ");
        inputForm.add(directionLabel, 0,5);

        ObservableList<Boolean> direction = FXCollections.observableArrayList();
        direction.addAll(true,false);

        directionSelect = new ComboBox<>();
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
        inputForm.add(directionSelect, 1,5);

        //Calculate Button
        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> recalculateValues());
        inputForm.add(calculateButton,0,6,2,1);
        calculateButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setFillWidth(calculateButton,true);

        thresholdSelect = new ComboBox<>();
        thresholdSelect.setItems(FXCollections.observableArrayList(runwaySelect.getValue().getRunways()));
        thresholdSelect.getSelectionModel().selectFirst();
        thresholdSelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(Runway runway) {
                if (runway == null ) return "Select Runway";
                return runway.getName();
            }
            @Override
            public Runway fromString(String s) {
                return null;
            }
        });
        inputForm.add(thresholdSelect, 0, 7, 2, 1);


        inputForm.getColumnConstraints().add(new ColumnConstraints(125));
        inputForm.getColumnConstraints().add(new ColumnConstraints(150));


        // back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> app.loadMenu());
        backButton.setMaxWidth(Double.MAX_VALUE);


        AnchorPane.setLeftAnchor(inputForm,0d);
        AnchorPane.setTopAnchor(inputForm,0d);


        //Opening Views
        Button sideOn = new Button("Side");
        sideOn.setOnAction(e -> {
            SideViewScene scene = new SideViewScene(app);
            scene.build();
            runwayUpdatedListeners.add(scene.getView()::runwayUpdated);
            scene.getView().runwayUpdated(currentRunway);
            Scene sce = scene.setScene();
            Stage stage = new Stage();
            stage.setTitle("Side on");
            stage.setScene(sce);
            stage.show();
        });

        AnchorPane.setRightAnchor(sideOn, 0d);
        AnchorPane.setTopAnchor(sideOn, 0d);
        AnchorPane.setBottomAnchor(backButton,0d);
        AnchorPane.setLeftAnchor(backButton,0d);


        Button s1 = new Button("Top");
        s1.setOnAction(e -> {
            TopDownScene scene = new TopDownScene(app);
            scene.build();
            runwayUpdatedListeners.add(scene.getView()::runwayUpdated);
            scene.getView().runwayUpdated(currentRunway);
            Scene sce = scene.setScene();
            Stage stage = new Stage();
            stage.setTitle("Top Down");
            stage.setScene(sce);
            stage.show();
        });
        AnchorPane.setRightAnchor(s1, 0d);
        AnchorPane.setBottomAnchor(s1, 0d);


        distanceFirstLabel.setText("Distance to " + runwaySelect.getValue().getFirst().getName() + ":");
        distanceSecondLabel.setText("Distance to " + runwaySelect.getValue().getSecond().getName() + ":");

        root.getChildren().addAll(sideOn, s1, inputForm,backButton);
    }

    private void recalculateValues(){
        Runway chosenRunway = thresholdSelect.getValue();
        Obstacle chosenObstacle = obstacleSelect.getValue();
        int chosenDistanceFromCentreLine = 0;
        int chosenDistance;

        try {
            chosenDistance = Integer.parseInt(distanceFirstInput.getCharacters().toString());
            chosenRunway.setObstacle(new ObstacleOnRunway(chosenObstacle,chosenDistance,chosenDistanceFromCentreLine));
            if (directionSelect.getValue()){
                currentRunway = Calculator.TowardsObstacle(chosenRunway);
            } else {
                currentRunway = Calculator.AwayFromObstacle(chosenRunway);
            }
            for (RunwayUpdatedListener r : runwayUpdatedListeners) {
                r.runwayUpdated(currentRunway);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    public void addRunwayListener (RunwayUpdatedListener r){
        runwayUpdatedListeners.add(r);
    }

    public void removeRunwayListener(int i){
        runwayUpdatedListeners.remove(i);
    }
}
