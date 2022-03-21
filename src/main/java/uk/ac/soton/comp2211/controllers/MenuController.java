package uk.ac.soton.comp2211.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.BaseView;
import uk.ac.soton.comp2211.views.CalculationsView;
import uk.ac.soton.comp2211.views.MenuView;
import uk.ac.soton.comp2211.views.SelectionView;

public class MenuController extends BaseController {

    //Model
    AirportModel model;

    //View
    MenuView view;

    public MenuController(BaseView view, AirportModel model) {
        super(view, model);
    }


    public void initialise() {
        //Airport Indicator
        view.getAirportIndicator().setText("Airport: " + model.selectedAirportProperty().get().getName());
        view.getAirportIndicator().setOnMouseClicked(e -> loadSelection());

        //Calculations
        view.getCalcButton().setOnMouseClicked(e -> loadCalculations());

        view.getExitButton().setOnMouseClicked(e -> Platform.exit());
    }


}
