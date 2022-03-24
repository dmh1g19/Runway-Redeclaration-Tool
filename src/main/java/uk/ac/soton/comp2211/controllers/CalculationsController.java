package uk.ac.soton.comp2211.controllers;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.util.StringConverter;
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.components.SideOnRunway;
import uk.ac.soton.comp2211.components.TopDownRunway;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.utility.Calculator;
import uk.ac.soton.comp2211.views.CalculationsView;
import uk.ac.soton.comp2211.views.MenuView;
import uk.ac.soton.comp2211.views.ViewsView;

import java.util.Map;
import java.util.Objects;

public class CalculationsController {

    //Model
    AirportModel model;

    //View
    CalculationsView view;

    public CalculationsController(CalculationsView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }

    public void initialise() {
        //Runway Select
        view.getRunwaySelect().setItems(FXCollections.observableArrayList(model.selectedAirportProperty().get().getRunways()));

        view.getRunwaySelect().setConverter(new StringConverter<>() {
            @Override
            public String toString(PhysicalRunway physicalRunway) {
                if (physicalRunway == null) return "Select Runway";
                return physicalRunway.getName();
            }

            @Override
            public PhysicalRunway fromString(String s) {
                return null;
            }
        });

        view.getRunwaySelect().valueProperty().addListener((observableValue, oldR, newR) -> {
            view.getLowerThreshold().setText(newR.getLowerThreshold());
            view.getUpperThreshold().setText(newR.getUpperThreshold());
            //Runway To Show
            view.getRunwayToShow().setItems(FXCollections.observableArrayList(model.getRunwayStates(newR)));
            view.getRunwayToShow().getSelectionModel().selectFirst();

            //Runway Values to be Shown
            view.getOriginalRunways().getItems().clear();
            view.getOriginalRunways().getItems().add(newR.getFirst());
            view.getOriginalRunways().getItems().add(newR.getSecond());
        });
        view.getRunwaySelect().getSelectionModel().selectFirst();

        //Obstacle
        view.getObstacleSelect().setItems(model.preDefinedObstaclesProperty());
        view.getObstacleSelect().setConverter(new StringConverter<>() {
            @Override
            public String toString(Obstacle obstacle) {
                if (obstacle == null) return "Select Obstacle";
                return obstacle.getName();
            }
            @Override
            public Obstacle fromString(String s) {
                return null;
            }
        });
        view.getObstacleSelect().valueProperty().addListener((observableValue, oldObs, newObs) -> {
            view.getObstacleHeight().setText(String.valueOf(newObs.getHeight()));
            view.getObstacleWidth().setText(String.valueOf(newObs.getWidth()));
            view.getObstacleLength().setText(String.valueOf(newObs.getLength()));


            view.getObstacleHeight().setStyle("-fx-text-box-border: cyan;");
            view.getObstacleWidth().setStyle("-fx-text-box-border: cyan;");
            view.getObstacleLength().setStyle("-fx-text-box-border: cyan;");
            PauseTransition pauseTransition = new PauseTransition(
                    Duration.seconds(1)
            );
            pauseTransition.setOnFinished(e -> {
                view.getObstacleHeight().setStyle(null);
                view.getObstacleWidth().setStyle(null);
                view.getObstacleLength().setStyle(null);
            });
            pauseTransition.play();
        });

        //Obstacle Info
        view.getObstacleHeight().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\\\d*)?")) view.getObstacleHeight().setText(oldV);
        });
        view.getObstacleWidth().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\\\d*)?")) view.getObstacleWidth().setText(oldV);
        });
        view.getObstacleLength().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\\\d*)?")) view.getObstacleLength().setText(oldV);
        });


        //Lower Threshold
        view.getLowerThreshold().setText(view.getRunwaySelect().getValue().getLowerThreshold());
        view.getDistanceLowerThreshold().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\\\d*)?")) view.getDistanceLowerThreshold().setText(oldV);
        });

        view.getSectionLowerThreshold().setItems(FXCollections.observableArrayList(Direction.TOWARDS,Direction.AWAYOVER));
        view.getSectionLowerThreshold().getSelectionModel().selectFirst();
        view.getSectionLowerThreshold().setConverter(new StringConverter<>() {
            @Override
            public String toString(Direction direction) {
                if (direction == Direction.TOWARDS) return "Before Obstacle";
                return "After Obstacle";
            }
            @Override
            public Direction fromString(String s) {
                return null;
            }
        });

        //Upper Threshold
        view.getUpperThreshold().setText(view.getRunwaySelect().getValue().getUpperThreshold());
        view.getDistanceUpperThreshold().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\\\d*)?")) view.getDistanceUpperThreshold().setText(oldV);
        });

        view.getSectionUpperThreshold().setItems(FXCollections.observableArrayList(Direction.TOWARDS,Direction.AWAYOVER));
        view.getSectionUpperThreshold().getSelectionModel().selectFirst();
        view.getSectionUpperThreshold().setConverter(new StringConverter<>() {
            @Override
            public String toString(Direction direction) {
                if (direction == Direction.TOWARDS) return "Before Obstacle";
                return "After Obstacle";
            }
            @Override
            public Direction fromString(String s) {
                return null;
            }
        });

        //Back Button
        view.getBackButton().setOnAction(e -> loadMenu());

        //Runway View Buttons
        view.getTopDownView().setOnAction(e -> loadTopDownView());
        view.getSideOnView().setOnAction(e -> loadSideOnView());

        //RunwayToShow
        view.getRunwayToShow().setItems(FXCollections.observableArrayList(
                model.getRunwayStates(view.getRunwaySelect().getValue())));
        view.getRunwayToShow().getSelectionModel().selectFirst();
        view.getRunwayToShow().setConverter(new StringConverter<>() {
            @Override
            public String toString(Pair<Runway, State> runwayStatePair) {
                if (runwayStatePair == null) return "Select RWY in use";
                return runwayStatePair.getKey().getName() + " " + runwayStatePair.getValue().toString();
            }

            @Override
            public Pair<Runway, State> fromString(String s) {
                return null;
            }
        });
        view.getRunwayToShow().valueProperty().addListener((observableValue, oldPair, newPair) -> {
            if (model.redeclaredRunwaysProperty().get() == null) return;
            if (newPair == null) return;
            if (Objects.equals(model.redeclaredRunwaysProperty().get().getKey().getRunway().getName(), newPair.getKey().getName())) {
                model.redeclaredRunwayProperty().set(model.redeclaredRunwaysProperty().get().getKey());
            } else {
                model.redeclaredRunwayProperty().set(model.redeclaredRunwaysProperty().get().getValue());
            }
            model.stateProperty().set(newPair.getValue());
        });

        //Calculate Button
        view.getCalculateButton().setOnAction(e -> {
            view.getObstacleLength().getStyleClass().remove("error");
            view.getObstacleWidth().getStyleClass().remove("error");
            view.getObstacleHeight().getStyleClass().remove("error");

            view.getDistanceLowerThreshold().getStyleClass().remove("error");
            view.getDistanceUpperThreshold().getStyleClass().remove("error");

            boolean error = false;
            //Checking for empty inputs
            if (view.getObstacleLength().getText().equals("")) {
                view.getObstacleLength().getStyleClass().add("error");
                error = true;
            }
            if (view.getObstacleWidth().getText().equals("")) {
                view.getObstacleWidth().getStyleClass().add("error");
                error = true;
            }
            if (view.getObstacleHeight().getText().equals("")) {
                view.getObstacleHeight().getStyleClass().add("error");
                error = true;
            }
            if (view.getDistanceLowerThreshold().getText().equals("")) {
                view.getDistanceLowerThreshold().getStyleClass().add("error");
                error = true;
            }
            if (view.getDistanceUpperThreshold().getText().equals("")) {
                view.getDistanceUpperThreshold().getStyleClass().add("error");
                error = true;
            }

            if (error) return;
            error = false;

            //Testing for invalid inputs
            try {
                int test = Integer.parseInt(view.getObstacleHeight().getText());
                if(test >= 1000){
                    error=true;
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Height too large");
                    a.show();
                }
                if(test <= 0){
                    error=true;
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Height too small");
                    a.show();
                }

            }
            catch(Exception y) {
                error=true;
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Height parameter.");
                a.show();
            }


            try {
                int test = Integer.parseInt(view.getObstacleLength().getText());
                if(test >= 1000){
                    error=true;
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Length too large");
                    a.show();
                }
                if(test <= 0){
                    error=true;
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Length too small");
                    a.show();
                }

            }
            catch(Exception y) {
                error=true;
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Length parameter.");
                a.show();
            }


            try {
                int test = Integer.parseInt(view.getObstacleWidth().getText());
                if (test >= 1000) {
                    error = true;
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Width too large");
                    a.show();
                }
                if (test <= 0) {
                    error = true;
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Width too small");
                    a.show();
                }
            }
            catch(Exception y) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Width parameter.");
                error=true;
                a.show();
            }
            if (error) return;

            //Calculate values
            int obstacleHeight = Integer.parseInt(view.getObstacleHeight().getText());
            int obstacleWidth = Integer.parseInt(view.getObstacleWidth().getText());
            int obstacleLength = Integer.parseInt(view.getObstacleLength().getText());

            int lowerDistance = Integer.parseInt(view.getDistanceLowerThreshold().getText());
            int upperDistance = Integer.parseInt(view.getDistanceUpperThreshold().getText());
            ObstacleOnRunway lowerObstacleOnRunway = new ObstacleOnRunway
                    ("lowerObs", obstacleHeight, obstacleLength, obstacleWidth, lowerDistance, 0);

            ObstacleOnRunway upperObstacleOnRunway = new ObstacleOnRunway
                    ("upperObs", obstacleHeight, obstacleLength, obstacleWidth, upperDistance, 0);

            RedeclaredRunway redeclaredRunwayLower = null;
            if (view.getSectionLowerThreshold().getValue().equals(Direction.TOWARDS)) {
                try {
                    redeclaredRunwayLower = Calculator.TowardsObstacle(view.getRunwaySelect().getValue().getFirst(), lowerObstacleOnRunway);
                } catch (Calculator.IncorrectObstacleException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    redeclaredRunwayLower = Calculator.AwayFromObstacle(view.getRunwaySelect().getValue().getFirst(), lowerObstacleOnRunway);
                } catch (Calculator.IncorrectObstacleException ex) {
                    ex.printStackTrace();
                }
            }
            RedeclaredRunway redeclaredRunwayUpper = null;
            if (view.getSectionUpperThreshold().getValue().equals(Direction.TOWARDS)) {
                try {
                    redeclaredRunwayUpper = Calculator.TowardsObstacle(view.getRunwaySelect().getValue().getSecond(), upperObstacleOnRunway);
                } catch (Calculator.IncorrectObstacleException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    redeclaredRunwayUpper = Calculator.AwayFromObstacle(view.getRunwaySelect().getValue().getSecond(), upperObstacleOnRunway);
                } catch (Calculator.IncorrectObstacleException ex) {
                    ex.printStackTrace();
                }
            }
            //Update model with new Values
            model.redeclaredRunwaysProperty().set(new Pair<>(redeclaredRunwayLower,redeclaredRunwayUpper));

            //Set model to calculated values
            if (Objects.equals(model.redeclaredRunwaysProperty().get().getKey().getRunway().getName(),
                    view.getRunwayToShow().getValue().getKey().getName())) {
                model.redeclaredRunwayProperty().set(model.redeclaredRunwaysProperty().get().getKey());
            } else {
                model.redeclaredRunwayProperty().set(model.redeclaredRunwaysProperty().get().getValue());
            }
            model.stateProperty().set(view.getRunwayToShow().getValue().getValue());

            //Redeclared Runway Values to Show
            view.getRedeclaredRunways().getItems().clear();
            view.getRedeclaredRunways().getItems().add(model.redeclaredRunwaysProperty().get().getKey());
            view.getRedeclaredRunways().getItems().add(model.redeclaredRunwaysProperty().get().getValue());

            //Show Breakdown
            view.getBreakdown().getChildren().clear();
            Map<String, String> lowerMap = Calculator.calculationBreakdown(view.getRunwaySelect().getValue().getFirst(),
                    lowerObstacleOnRunway, view.getSectionLowerThreshold().getValue()); // Get values for lwrthr
            Text lowerThr = new Text(view.getLowerThreshold().getText());
            lowerThr.getStyleClass().add("breakdownThreshold");
            view.getBreakdown().getChildren().add(lowerThr);
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));

            view.getBreakdown().getChildren().add(new Text(lowerMap.get("TORA")));
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));
            view.getBreakdown().getChildren().add(new Text(lowerMap.get("TODA")));
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));
            view.getBreakdown().getChildren().add(new Text(lowerMap.get("ASDA")));
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));
            view.getBreakdown().getChildren().add(new Text(lowerMap.get("LDA")));

            view.getBreakdown().getChildren().add(new Text(System.lineSeparator())); //Separate THRs
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));

            Map<String, String> upperMap = Calculator.calculationBreakdown(view.getRunwaySelect().getValue().getSecond(),
                    lowerObstacleOnRunway, view.getSectionLowerThreshold().getValue()); // Get values for uprthr
            Text upperThr = new Text(view.getUpperThreshold().getText());
            upperThr.getStyleClass().add("breakdownThreshold");
            view.getBreakdown().getChildren().add(upperThr);
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));

            view.getBreakdown().getChildren().add(new Text(upperMap.get("TORA")));
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));
            view.getBreakdown().getChildren().add(new Text(upperMap.get("TODA")));
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));
            view.getBreakdown().getChildren().add(new Text(upperMap.get("ASDA")));
            view.getBreakdown().getChildren().add(new Text(System.lineSeparator()));
            view.getBreakdown().getChildren().add(new Text(upperMap.get("LDA")));
        });

    }

    public void loadMenu() {
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView, model);
        view.getView().getScene().setRoot(menuView.getView());
    }

    public void loadTopDownView() {
        int width = 700;
        int height = 400;

        ViewsView viewsView = new ViewsView(new TopDownRunway(width, height));
        ViewsController viewsController = new ViewsController(viewsView, model);
        Stage newWindow = new Stage();
        newWindow.setTitle("Top Down View");

        newWindow.setScene(new Scene(viewsView.getView(), width, height));
        newWindow.getScene().setFill(Color.color(0.1,0.1,0.1));
        newWindow.setResizable(false);
        newWindow.show();

    }

    public void loadSideOnView() {
        int width = 700;
        int height = 400;

        ViewsView viewsView = new ViewsView(new SideOnRunway(width,height));
        ViewsController viewsController = new ViewsController(viewsView, model);
        Stage newWindow = new Stage();
        newWindow.setTitle("Side On View");
        newWindow.setScene(new Scene(viewsView.getView(), width, height));
        newWindow.setResizable(false);
        newWindow.show();
    }

}
