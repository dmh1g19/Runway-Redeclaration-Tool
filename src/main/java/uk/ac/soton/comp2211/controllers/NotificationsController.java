package uk.ac.soton.comp2211.controllers;

import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.NotificationsView;
import uk.ac.soton.comp2211.views.SettingsView;

public class NotificationsController {

    //Model
    AirportModel model;

    //View
    NotificationsView view;

    public NotificationsController(NotificationsView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }

    public void initialise() {

    }

}
