package uk.ac.soton.comp2211.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.*;

public class MenuController {

    //Model
    AirportModel model;

    //View
    MenuView view;

    public MenuController(MenuView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }


    public void initialise() {
        //Airport Indicator
        view.getAirportIndicator().setText("Airport: " + model.selectedAirportProperty().get().getName());
        view.getAirportIndicator().setOnMouseClicked(e -> loadSelection());

        view.getObstaclesButton().setOnMouseClicked( e -> loadObstacleMenu());

        //Calculations
        view.getCalcButton().setOnMouseClicked(e -> loadCalculations());

        view.getExitButton().setOnMouseClicked(e -> Platform.exit());
    }

    public void loadSelection() {
        SelectionView selectionView = new SelectionView();
        SelectionController selectionController = new SelectionController(selectionView, model);
        view.getView().getScene().setRoot(selectionView.getView());
    }

    public void loadObstacleMenu() {
        ObstacleCreationView obstaclesView = new ObstacleCreationView();
        ObstacleCreationController obstacleCreationController = new ObstacleCreationController(obstaclesView, model);
        view.getView().getScene().setRoot(obstaclesView.getView());
    }

    public void loadCalculations() {
        CalculationsView calculationsView = new CalculationsView();
        CalculationsController calculationsController = new CalculationsController(calculationsView, model);
        view.getView().getScene().setRoot(calculationsView.getView());
    }

}
