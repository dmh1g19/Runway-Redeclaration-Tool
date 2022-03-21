package uk.ac.soton.comp2211.controllers;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.springframework.beans.factory.xml.SimpleConstructorNamespaceHandler;
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.components.SideOnRunway;
import uk.ac.soton.comp2211.components.TopDownRunway;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.utility.Calculator;
import uk.ac.soton.comp2211.views.CalculationsView;
import uk.ac.soton.comp2211.views.MenuView;
import uk.ac.soton.comp2211.views.ViewsView;

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
        view.getRunwaySelect().getSelectionModel().selectFirst();

        view.getRunwaySelect().setConverter(new StringConverter<>() {
            @Override
            public String toString(PhysicalRunway physicalRunway) {
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
        });

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
            if (!newV.matches("\\d*(\\.\\d*)?")) view.getObstacleHeight().setText(oldV);
        });
        view.getObstacleWidth().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\.\\d*)?")) view.getObstacleWidth().setText(oldV);
        });
        view.getObstacleLength().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\.\\d*)?")) view.getObstacleLength().setText(oldV);
        });


        //Lower Threshold
        view.getLowerThreshold().setText(view.getRunwaySelect().getValue().getLowerThreshold());
        view.getDistanceLowerThreshold().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\.\\d*)?")) view.getDistanceLowerThreshold().setText(oldV);
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
            if (!newV.matches("\\d*(\\.\\d*)?")) view.getDistanceUpperThreshold().setText(oldV);
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

        //Calculate Button
        view.getCalculateButton().setOnAction(e -> {
            view.getObstacleLength().getStyleClass().remove("error");
            view.getObstacleWidth().getStyleClass().remove("error");
            view.getObstacleHeight().getStyleClass().remove("error");

            view.getDistanceLowerThreshold().getStyleClass().remove("error");
            view.getDistanceUpperThreshold().getStyleClass().remove("error");

            boolean error = false;

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

            int obstacleHeight = Integer.parseInt(view.getObstacleHeight().getText());
            int obstacleWidth = Integer.parseInt(view.getObstacleWidth().getText());
            int obstacleLength = Integer.parseInt(view.getObstacleLength().getText());

            int distance = Integer.parseInt(view.getDistanceLowerThreshold().getText());
            ObstacleOnRunway obstacleOnRunway = new ObstacleOnRunway("Obs", obstacleHeight, obstacleLength, obstacleWidth, distance, 0);


            if (view.getSectionLowerThreshold().getValue().equals(Direction.TOWARDS)) {
             try {
                    RedeclaredRunway redeclaredRunway = Calculator.TowardsObstacle(view.getRunwaySelect().getValue().getFirst(), obstacleOnRunway);

                    model.setRedeclaredRunway(redeclaredRunway);
                    model.setState(State.LANDING);
                } catch (Calculator.IncorrectObstacleException ex) {
                    ex.printStackTrace();
                }
            }
            if (view.getSectionLowerThreshold().getValue().equals(Direction.AWAYOVER)) {
                try {
                    RedeclaredRunway redeclaredRunway = Calculator.AwayFromObstacle(view.getRunwaySelect().getValue().getFirst(), obstacleOnRunway);

                    model.setRedeclaredRunway(redeclaredRunway);
                    model.setState(State.LANDING);
                } catch (Calculator.IncorrectObstacleException ex) {
                    ex.printStackTrace();
                }
            }

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
