package uk.ac.soton.comp2211.controllers;

import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.MenuView;
import uk.ac.soton.comp2211.views.SelectionView;
import uk.ac.soton.comp2211.views.SettingsView;

public class SettingsController {

    //Model
    AirportModel model;

    //View
    SettingsView view;

    public SettingsController(SettingsView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }

    public void initialise(){
        view.getFontsBox().getSelectionModel().select(model.getFontSize());
        view.getBackButton().setOnMouseClicked(mouseEvent -> loadMenu());

        view.getFontsBox().getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
           updateFont(oldValue, newValue);
        });



    }

    public void loadMenu() {
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView, model);
        view.getView().getScene().setRoot(menuView.getView());
    }


    private  void updateFont(String oldFont,String newFont){
        view.getView().getScene().getStylesheets().remove(App.class.getResource(oldFont+"Font.css").toExternalForm());
        view.getView().getScene().getStylesheets().add(App.class.getResource(newFont+"Font.css").toExternalForm());
        switch (newFont) {
            case "Small" -> model.setFontSize(0);
            case "Medium" -> model.setFontSize(1);
            case "Large" -> model.setFontSize(2);
        }
    }


}
