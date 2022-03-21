package uk.ac.soton.comp2211.controllers;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.MenuView;
import uk.ac.soton.comp2211.views.SelectionView;

import java.awt.*;


public class SelectionController {

    //Model
    AirportModel model;

    //View
    SelectionView view;

    public SelectionController(SelectionView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }

    public void initialise() {
        //Airport Selection
        view.getAirportSelectionList().itemsProperty().bind(model.airportListProperty());
        if (!(model.selectedAirportProperty() == null)) {
            view.getAirportSelectionList().getSelectionModel().select(model.selectedAirportProperty().get());
        }

        view.getAirportSelectionList().setConverter(new StringConverter<>() {
            @Override
            public String toString(Airport airport) {
                if (airport == null) return "Select Airport";
                return airport.getName();
            }

            @Override
            public Airport fromString(String s) {
                return null;
            }
        });
        view.getAirportSelectionList().setEditable(false);

        //Select Button
        view.getSelectButton().setOnAction(e -> {
            if (view.getAirportSelectionList().getValue() == null) return;
            model.selectedAirportProperty().set(view.getAirportSelectionList().getValue());
            loadMenu();
        });

    }

    public void loadMenu() {
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView, model);
        view.getView().getScene().setRoot(menuView.getView());
    }

}
