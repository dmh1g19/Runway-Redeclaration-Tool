package uk.ac.soton.comp2211.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.xml.SimpleConstructorNamespaceHandler;
import uk.ac.soton.comp2211.airport.Obstacle;
import uk.ac.soton.comp2211.airport.PhysicalRunway;
import uk.ac.soton.comp2211.components.SideOnRunway;
import uk.ac.soton.comp2211.components.TopDownRunway;
import uk.ac.soton.comp2211.models.AirportModel;
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

        view.getObstacleSelect().valueProperty().addListener((observableValue, oldObs, newObs) -> {
            view.getObstacleHeight().setText(String.valueOf(newObs.getHeight()));
            view.getObstacleWidth().setText(String.valueOf(newObs.getWidth()));
            view.getObstacleLength().setText(String.valueOf(newObs.getLength()));
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


        //Upper Threshold
        view.getUpperThreshold().setText(view.getRunwaySelect().getValue().getUpperThreshold());
        view.getDistanceUpperThreshold().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\.\\d*)?")) view.getDistanceUpperThreshold().setText(oldV);
        });

        //Back Button
        view.getBackButton().setOnAction(e -> {
            loadMenu();
        });

        //Runway View Buttons
        view.getTopDownView().setOnAction(e -> loadTopDownView());
        view.getSideOnView().setOnAction(e -> loadSideOnView());
    }

    public void loadMenu() {
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView, model);
        view.getView().getScene().setRoot(menuView.getView());
    }

    public void loadTopDownView() {
        int width = 600;
        int height = 800;

        ViewsView viewsView = new ViewsView(new TopDownRunway(width, height));
        ViewsController viewsController = new ViewsController(viewsView, model);
        Stage newWindow = new Stage();
        newWindow.setTitle("Top Down View");
        newWindow.setScene(new Scene(viewsView.getView(), width, height));
        newWindow.setResizable(false);
    }

    public void loadSideOnView() {
        int width = 600;
        int height = 800;

        ViewsView viewsView = new ViewsView(new SideOnRunway(width,height));
        ViewsController viewsController = new ViewsController(viewsView, model);
        Stage newWindow = new Stage();
        newWindow.setTitle("Side On View");
        newWindow.setScene(new Scene(viewsView.getView(), width, height));
        newWindow.setResizable(false);
    }

}
