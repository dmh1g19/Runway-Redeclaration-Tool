package uk.ac.soton.comp2211.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import uk.ac.soton.comp2211.airport.Obstacle;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.ObstacleCreationView;
import uk.ac.soton.comp2211.views.SelectionView;

import java.util.ArrayList;

public class ObstacleCreationController {

    //Model
    AirportModel model;

    //View
    ObstacleCreationView view;

    public ObstacleCreationController(ObstacleCreationView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }
    public void initialise() {
        //Adding Obstacles to view
        ObservableList<Obstacle> obstacles = model.preDefinedObstaclesProperty().get();
        for(Obstacle obstacle : obstacles){
            HBox obstacleB = new HBox();
            Button button = new Button("Remove Obstacle");
            Label obstacleName = new Label(obstacle.getName());
            obstacleName.setPrefWidth(200);
            obstacleName.setMaxWidth(200);

            obstacleB.getChildren().addAll(obstacleName, button);
            button.setOnAction(e -> {
                view.getObsForm().getChildren().remove(obstacleB);
                model.preDefinedObstaclesProperty().remove(obstacle);
            });

            view.getObsForm().getChildren().add(obstacleB);
        }


        //Obstacle data only contains numbers
        view.getObstacleHeight().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\\\d*)?")) view.getObstacleHeight().setText(oldV);
        });
        view.getObstacleWidth().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\\\d*)?")) view.getObstacleWidth().setText(oldV);
        });
        view.getObstacleLength().textProperty().addListener((observableValue, oldV, newV) -> {
            if (!newV.matches("\\d*(\\\\d*)?")) view.getObstacleLength().setText(oldV);
        });

        view.getAddObstacle().setOnMouseClicked(e-> addObstacle());


        //adding back button
        view.getBackButton().setOnMouseClicked(e->loadSelection());

        //adding a push to xml button
        view.getWriteObstacles().setOnMouseClicked(e -> model.writeObstaclesToXML());

        //setting up remove obstacle buttons

    }

    private void addObstacle(){
        view.getObstacleName().getStyleClass().remove("error");
        view.getObstacleLength().getStyleClass().remove("error");
        view.getObstacleWidth().getStyleClass().remove("error");
        view.getObstacleHeight().getStyleClass().remove("error");

        boolean error=false;
        if (view.getObstacleLength().getText().equals("")) {
            view.getObstacleLength().getStyleClass().add("error");
            error = true;
        }
        if (view.getObstacleName().getText().equals("")) {
            view.getObstacleName().getStyleClass().add("error");
            error = true;
        }
        if (view.getObstacleWidth().getText().equals("")) {
            view.getObstacleWidth().getStyleClass().add("error");
            error = true;
        }
        if (view.getObstacleHeight().getText().equals("")) {
            view.getObstacleHeight().getStyleClass().add("error");
            error = true;
        }

        try {
            int test = Integer.parseInt(view.getObstacleHeight().getText());
            if(test >= 1000){
                error=true;
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Height to large");
                a.show();
            }
            if(test <= 0){
                error=true;
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Height to small");
                a.show();
            }

        }
        catch(Exception e) {
            error=true;
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Invalid Height parameter.");
            a.show();
        }


        try {
            int test = Integer.parseInt(view.getObstacleLength().getText());
            if(test >= 1000){
                error=true;
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Length to large");
                a.show();
            }
            if(test <= 0){
                error=true;
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Length to small");
                a.show();
            }

        }
        catch(Exception e) {
            error=true;
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Invalid Length parameter.");
            a.show();
        }


        try {
            int test = Integer.parseInt(view.getObstacleWidth().getText());
            if(test >= 1000){
                error=true;
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Width to large");
                a.show();
            }
            if(test <= 0){
                error=true;
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Width to small");
                a.show();
            }

        }
        catch(Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Invalid Width parameter.");
            error=true;
            a.show();
        }

        if(!error){
            try {
                Obstacle obstacle = new Obstacle(view.getObstacleName().getText(),
                        Integer.parseInt(view.getObstacleHeight().getText()),
                        Integer.parseInt(view.getObstacleLength().getText()),
                        Integer.parseInt(view.getObstacleWidth().getText()));
                model.preDefinedObstaclesProperty().add(obstacle);

                //Add Obstacle to write side List
                HBox obstacleB = new HBox();
                Button button = new Button("Remove Obstacle");
                Label obstacleName = new Label(obstacle.getName());
                obstacleName.setPrefWidth(200);
                obstacleName.setMaxWidth(200);

                obstacleB.getChildren().addAll(obstacleName, button);
                button.setOnAction(e -> {
                    view.getObsForm().getChildren().remove(obstacleB);
                    model.preDefinedObstaclesProperty().remove(obstacle);
                });

                view.getObsForm().getChildren().add(obstacleB);

                view.getObstacleHeight().setText("");
                view.getObstacleLength().setText("");
                view.getObstacleWidth().setText("");
                view.getObstacleName().setText("");

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Obstacle Added");
                a.show();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }else {
            System.out.println("error with inputs");
        }

    }


    public void loadSelection() {
        SelectionView selectionView = new SelectionView();
        SelectionController selectionController = new SelectionController(selectionView, model);
        view.getView().getScene().setRoot(selectionView.getView());
    }
}
