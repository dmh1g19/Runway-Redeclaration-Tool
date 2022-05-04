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
        view.getRunwayView().setBlastProtection(model.getBlastProtection());
        initialise();
        if(model.isCustomColours()){
            view.getView().setStyle(model.getCustomColours());
        }
    }

    public void initialise() {
        model.redeclaredRunwayProperty().addListener((observableValue, oldV, newV) -> {
            if (model.redeclaredRunwaysProperty().get().getKey().equals(newV)) {
                view.getRunwayView().draw(newV, model.redeclaredRunwaysProperty().get().getValue(), model.stateProperty().get());
            } else {
                view.getRunwayView().draw(newV, model.redeclaredRunwaysProperty().get().getKey(), model.stateProperty().get());
            }
        });

        model.stateProperty().addListener((observableValue, oldV, newV) -> {
            if (model.redeclaredRunwaysProperty().get().getKey().equals(model.redeclaredRunwayProperty().get())) {
                view.getRunwayView().draw(model.redeclaredRunwayProperty().get(), model.redeclaredRunwaysProperty().get().getValue(), newV);
            } else {
                view.getRunwayView().draw(model.redeclaredRunwayProperty().get(), model.redeclaredRunwaysProperty().get().getKey(), newV);
            }
        });

        if (!(model.redeclaredRunwayProperty().get() == null) && !(model.stateProperty().get() == null)) {
            if (model.redeclaredRunwaysProperty().get().getKey().equals(model.redeclaredRunwayProperty().get())) {
                view.getRunwayView().draw(model.redeclaredRunwayProperty().get(), model.redeclaredRunwaysProperty().get().getValue(), model.stateProperty().get());
            } else {
                view.getRunwayView().draw(model.redeclaredRunwayProperty().get(), model.redeclaredRunwaysProperty().get().getKey(), model.stateProperty().get());
            }
        }
        model.blastProtectionProperty().addListener((observableValue, oldV, newV) ->{
            view.getRunwayView().setBlastProtection(newV.intValue());
        });

    }

}
