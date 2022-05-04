package uk.ac.soton.comp2211.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;
import javafx.util.Pair;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.PhysicalRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.NotificationsView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        // UPDATE WHEN ACTION ADDED
        model.actionsProperty().addListener((observableValue, pairs, t1) -> {
            view.getNotifFlow().getChildren().clear();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            for (Pair<Date,String> notif : model.actionsProperty().get()) {
                var info = notif.getValue();

                var text = new Text(sdf.format(notif.getKey()) + " | " + info);
                text.setStyle("-fx-font-size: 16");
                if (notif.getValue().startsWith("Error")) text.setStyle("-fx-fill: red; -fx-font-size: 16");
                view.getNotifFlow().getChildren().add(text);
                view.getNotifFlow().getChildren().add(new Text(System.lineSeparator()));
            }
        });

        //INITIAL LOAD
        view.getNotifFlow().getChildren().clear();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for (Pair<Date,String> notif : model.actionsProperty().get()) {
            var info = notif.getValue();

            var text = new Text(sdf.format(notif.getKey()) + " | " + info);
            text.setStyle("-fx-font-size: 16");
            if (notif.getValue().startsWith("Error")) text.setStyle("-fx-fill: red; -fx-font-size: 16");
            view.getNotifFlow().getChildren().add(text);
            view.getNotifFlow().getChildren().add(new Text(System.lineSeparator()));
        }

        view.getScrollPane().vvalueProperty().bind(view.getNotifFlow().heightProperty());
    }

}
