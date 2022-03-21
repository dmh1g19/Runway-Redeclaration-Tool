package uk.ac.soton.comp2211.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.State;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.ViewsView;

public class ViewsController {

    //Model
    AirportModel model;

    //View
    ViewsView view;

    public ViewsController(ViewsView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }

    public void initialise() {
        model.redeclaredRunwayProperty().addListener((observableValue, oldV, newV) -> {
            view.getRunwayView().draw(newV, model.stateProperty().get());
        });

        model.stateProperty().addListener((observableValue, oldV, newV) -> {
            view.getRunwayView().draw(model.redeclaredRunwayProperty().get(), newV);
        });


    }

}
