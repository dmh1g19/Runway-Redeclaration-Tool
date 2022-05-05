package uk.ac.soton.comp2211.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
        if(model.isCustomColours()){
            view.getView().setStyle(model.getCustomColours());
        }
    }


    public void initialise() {
        //Airport Indicator
        if (!(model.selectedAirportProperty().get() == null)) view.getAirportIndicator().setText("Airport: " + model.selectedAirportProperty().get().getName());
        else view.getAirportIndicator().setText("Airport: None selected");

        //Calculations
        view.getCalcButton().setOnMouseClicked(e -> {
            if (model.selectedAirportProperty().get() == null) loadSelection();
            else loadCalculations();
        });

        view.getExitButton().setOnMouseClicked(e -> Platform.exit());
        view.getSettingsButton().setOnMouseClicked(e -> loadSettings());
        view.getNotifButton().setOnAction(e -> loadNotifications());
    }

    public void loadSelection() {
        SelectionView selectionView = new SelectionView();
        SelectionController selectionController = new SelectionController(selectionView, model);
        view.getView().getScene().setRoot(selectionView.getView());
    }

    public void loadSettings() {
        SettingsView settingsView = new SettingsView();
        SettingsController settingsController = new SettingsController(settingsView, model);
        view.getView().getScene().setRoot(settingsView.getView());
    }

    public void loadCalculations() {
        CalculationsView calculationsView = new CalculationsView();
        CalculationsController calculationsController = new CalculationsController(calculationsView, model);
        view.getView().getScene().setRoot(calculationsView.getView());
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
