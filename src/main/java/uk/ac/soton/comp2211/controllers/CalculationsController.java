package uk.ac.soton.comp2211.controllers;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.util.StringConverter;
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.components.SideOnRunway;
import uk.ac.soton.comp2211.components.TopDownRunway;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.utility.Calculator;
import uk.ac.soton.comp2211.utility.XMLUtil;
import uk.ac.soton.comp2211.views.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        if(model.isCustomColours()){
            view.getView().setStyle(model.getCustomColours());
        }
    }

    public void initialise() {
        //View Notifications
        view.getNotifButton().setOnAction(e -> loadNotifications());

        //Airport Indicator
        view.getAirportIndicator().setText("Airport: " + model.selectedAirportProperty().get().getName());

        //Change Airport
        view.getChangeAirport().setOnAction(e -> loadSelection());

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
            if (!newV.matches("[0-9]*")) view.getObstacleHeight().setText(oldV);
        });
        view.getObstacleWidth().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("[0-9]*")) view.getObstacleWidth().setText(oldV);
        });
        view.getObstacleLength().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("[0-9]*")) view.getObstacleLength().setText(oldV);
        });


        //Lower Threshold
        view.getLowerThreshold().setText(view.getRunwaySelect().getValue().getLowerThreshold());
        view.getDistanceLowerThreshold().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("-?[0-9]*")) view.getDistanceLowerThreshold().setText(oldV);
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
            if (!newV.matches("-?[0-9]*")) view.getDistanceUpperThreshold().setText(oldV);
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
        view.getTopDownView().setDisable(true);
        view.getSideOnView().setOnAction(e -> loadSideOnView());
        view.getSideOnView().setDisable(true);

            //Store Calculations Button
        view.getStoreCalculationButton().setDisable(true);
        view.getStoreCalculationButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
            fileChooser.setTitle("Store Calculations To File");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            fileChooser.setInitialFileName(formatter.format(date) + model.selectedAirportProperty().get().getName()
                    + model.redeclaredRunwaysProperty().get().getKey().getRunway().getName() + "-"
                    + model.redeclaredRunwaysProperty().get().getValue().getRunway().getName());
            File file = fileChooser.showSaveDialog(view.getView().getScene().getWindow());
            if (file == null) return;
            try {
                XMLUtil.storeCalculation(model.selectedAirportProperty().get(),model.redeclaredRunwaysProperty().get(), file);
                Alert importSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                importSuccess.setTitle("Success!");
                importSuccess.setContentText("Calculation was stored successfully");
                importSuccess.show();
            } catch (IOException ex) {
                Alert importFail = new Alert(Alert.AlertType.ERROR);
                importFail.setTitle("Error");
                importFail.setContentText("Error storing file");
                importFail.show();
            }
        });

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
            if (view.getDistanceLowerThreshold().getText().equals("") || view.getDistanceLowerThreshold().getText().equals("-")) {
                view.getDistanceLowerThreshold().getStyleClass().add("error");
                error = true;
            }
            if (view.getDistanceUpperThreshold().getText().equals("") || view.getDistanceUpperThreshold().getText().equals("-")) {
                view.getDistanceUpperThreshold().getStyleClass().add("error");
                error = true;
            }

            if (error)  {
                model.addAction("Error: Insufficient parameters in calculation inputs");
                return;
            }
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

            /**
             * CALCULATE NEW VALUES
             */
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
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Invalid obstacle position for runway: "+view.getRunwaySelect().getValue().getFirst().getName());
                    error=true;
                    a.show();
                    view.getDistanceLowerThreshold().setText("");
                }
            } else {
                try {
                    redeclaredRunwayLower = Calculator.AwayFromObstacle(view.getRunwaySelect().getValue().getFirst(), lowerObstacleOnRunway);
                } catch (Calculator.IncorrectObstacleException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Invalid obstacle position for runway: "+view.getRunwaySelect().getValue().getFirst().getName());
                    error=true;
                    a.show();
                    view.getDistanceLowerThreshold().setText("");
                }
            }
            RedeclaredRunway redeclaredRunwayUpper = null;
            if (view.getSectionUpperThreshold().getValue().equals(Direction.TOWARDS)) {
                try {
                    redeclaredRunwayUpper = Calculator.TowardsObstacle(view.getRunwaySelect().getValue().getSecond(), upperObstacleOnRunway);
                } catch (Calculator.IncorrectObstacleException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Invalid obstacle position for runway: "+view.getRunwaySelect().getValue().getSecond().getName());
                    error=true;
                    a.show();
                    view.getDistanceUpperThreshold().setText("");
                }
            } else {
                try {
                    redeclaredRunwayUpper = Calculator.AwayFromObstacle(view.getRunwaySelect().getValue().getSecond(), upperObstacleOnRunway);
                } catch (Calculator.IncorrectObstacleException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Invalid obstacle position for runway: "+view.getRunwaySelect().getValue().getSecond().getName());
                    error=true;
                    a.show();
                    view.getDistanceUpperThreshold().setText("");
                }
            }
            if (error)
                return;
            //Update model with new Values
            model.redeclaredRunwaysProperty().set(new Pair<>(redeclaredRunwayLower,redeclaredRunwayUpper));

            /**
             * SET MODEL TO CALCULATED VALUES
             */
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

            /**
             * ADD BREAKDOWN TEXT
             */
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
                    upperObstacleOnRunway, view.getSectionUpperThreshold().getValue()); // Get values for uprthr
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

            /**
             * ENABLE BUTTONS
             */
            view.getSideOnView().setDisable(false);
            view.getTopDownView().setDisable(false);
            view.getStoreCalculationButton().setDisable(false);

            // ADD ACTION TO MODEL
        });

        //blast proctection button
        view.getBlastProtection().setOnAction(e ->{

            TextInputDialog td = new TextInputDialog("Enter new distance");
            td.showAndWait().ifPresent(distanceStr ->{
                int blastDistance =0;
                try {
                    blastDistance = Integer.parseInt(distanceStr);
                }
                catch(Exception e1) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Please enter a number");
                    a.show();
                }
                if(blastDistance >1000){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("blast distance to large");
                    a.show();
                    return;
                }
                if(blastDistance <0){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("blast distance to small");
                    a.show();
                    return;
                }
                model.blastProtectionProperty().set(blastDistance);

            });



        });

    }

    public void loadMenu() {
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView, model);
        view.getView().getScene().setRoot(menuView.getView());
    }

    public void loadSelection() {
        SelectionView selectionView = new SelectionView();
        SelectionController selectionController = new SelectionController(selectionView, model);
        view.getView().getScene().setRoot(selectionView.getView());
    }

    public void loadTopDownView() {
        int width = 700;
        int height = 400;

        TopDownRunway runway = new TopDownRunway(width, height);
        ViewsView viewsView = new ViewsView(runway);
        ViewsController viewsController = new ViewsController(viewsView, model);
        Stage newWindow = new Stage();
        newWindow.setTitle("Top Down View");

        Circle compassClickbox = new Circle(width- 75, (height/10)+25, 25, Color.TRANSPARENT);
        compassClickbox.setOnMouseClicked((e) -> runway.toggleBearingAlligned());

        AnchorPane anchorPane = new AnchorPane();


        viewsView.getView().setOnScroll((e) -> runway.zoom(e.getDeltaY()/1000));

        viewsView.getView().setOnMousePressed((e) -> runway.enterScroll(e.getX(), e.getY()));
        viewsView.getView().setOnMouseDragged((e) -> runway.scroll(e.getX()-runway.getWidth()/2,e.getY()-runway.getHeight()/2));
        viewsView.getView().setOnMousePressed((e) -> runway.exitScroll(e.getX()-runway.getWidth()/2,e.getY()-runway.getHeight()/2));


        anchorPane.getChildren().add(viewsView.getView());
        anchorPane.getChildren().add(compassClickbox);

        newWindow.setScene(new Scene(anchorPane, width, height));
        anchorPane.setStyle("-fx-background-color: #1A1A1AFF");
        newWindow.setResizable(false);
        newWindow.show();

    }

    public void loadSideOnView() {
        int width = 700;
        int height = 400;

        SideOnRunway runway = new SideOnRunway(width,height);
        ViewsView viewsView = new ViewsView(runway);
        ViewsController viewsController = new ViewsController(viewsView, model);
        Stage newWindow = new Stage();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(viewsView.getView());

        viewsView.getView().setOnScroll((e) -> runway.zoom(e.getDeltaY()/1000));

        viewsView.getView().setOnMousePressed((e) -> runway.enterScroll(e.getX(), e.getY()));
        viewsView.getView().setOnMouseDragged((e) -> runway.scroll(e.getX()-runway.getWidth()/2,e.getY()-runway.getHeight()/2));
        viewsView.getView().setOnMousePressed((e) -> runway.exitScroll(e.getX()-runway.getWidth()/2,e.getY()-runway.getHeight()/2));

        newWindow.setTitle("Side On View");
        newWindow.setScene(new Scene(anchorPane, width, height));
        anchorPane.setStyle("-fx-background-color: #ADD8E6");
        newWindow.setResizable(false);
        newWindow.show();
    }

    public void loadNotifications() {
        int width = 600;
        int height = 400;

        NotificationsView notificationsView = new NotificationsView();
        NotificationsController notificationsController = new NotificationsController(notificationsView, model);
        Stage newWindow = new Stage();
        newWindow.setTitle("Notifications");
        newWindow.setScene(new Scene(notificationsView.getView(), width, height));
        newWindow.setResizable(false);
        newWindow.show();
    }

}
