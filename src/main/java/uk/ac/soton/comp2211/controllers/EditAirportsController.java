package uk.ac.soton.comp2211.controllers;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.PhysicalRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.utility.XMLUtil;
import uk.ac.soton.comp2211.views.EditAirportsView;
import uk.ac.soton.comp2211.views.SettingsView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EditAirportsController {

    //Model
    AirportModel model;

    //View
    EditAirportsView view;

    //
    ArrayList<Pair<ArrayList<TextField>,ArrayList<TextField>>> fields = new ArrayList<>();

    public EditAirportsController(EditAirportsView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }

    public void initialise() {
        //Save to File
        view.getSaveToFile().setOnAction(e -> {
            try {
                XMLUtil.exportAirports(new File("airports.xml"), model.airportListProperty().get());
            } catch (IOException ex) {
                view.getSaveToFile().setStyle("-fx-background-color: red");
                PauseTransition pauseTransition = new PauseTransition(
                        Duration.seconds(1)
                );
                pauseTransition.setOnFinished(tr -> {
                    view.getSaveToFile().setStyle(null);
                });
                pauseTransition.play();
            }
        });
        //Back button
        view.getBackButton().setOnAction(e -> loadSettings());

        //Add Airports To List
        loadAirports();

        model.airportListProperty().addListener((observableValue, airports, t1) -> {
            loadAirports();
        });

        //Edit Airports
        Button newRunway = new Button("Add Runway");
        newRunway.setOnAction(e -> {
            newPhysicalRunway();
        });
        view.getEditRunwaysVBox().getChildren().add(newRunway);

        view.getSaveAirport().setOnAction(e -> {
            try {
                if (fields.isEmpty()) throw new Exception("No Runways");

                ArrayList<PhysicalRunway> physicalRunways = new ArrayList<>();
                for (var prunway : fields) {
                    var runwayFields1 = prunway.getKey();
                    var runwayFields2 = prunway.getValue();
                    if (runwayFields1.size() != 12) throw new Exception("Not enough fields");
                    Runway runway1 = new Runway(runwayFields1.get(0).getText(), Integer.parseInt(runwayFields1.get(1).getText())
                            , Integer.parseInt(runwayFields1.get(2).getText()), Integer.parseInt(runwayFields1.get(3).getText())
                            , Integer.parseInt(runwayFields1.get(4).getText()), Integer.parseInt(runwayFields1.get(5).getText())
                            , Integer.parseInt(runwayFields1.get(6).getText()), Integer.parseInt(runwayFields1.get(7).getText())
                            , Integer.parseInt(runwayFields1.get(8).getText()), Integer.parseInt(runwayFields1.get(9).getText())
                            , Integer.parseInt(runwayFields1.get(10).getText()), Integer.parseInt(runwayFields1.get(11).getText()));

                    if (runwayFields2.size() != 12) throw new Exception("Not enough fields");
                    Runway runway2 = new Runway(runwayFields2.get(0).getText(), Integer.parseInt(runwayFields2.get(1).getText())
                            , Integer.parseInt(runwayFields2.get(2).getText()), Integer.parseInt(runwayFields2.get(3).getText())
                            , Integer.parseInt(runwayFields2.get(4).getText()), Integer.parseInt(runwayFields2.get(5).getText())
                            , Integer.parseInt(runwayFields2.get(6).getText()), Integer.parseInt(runwayFields2.get(7).getText())
                            , Integer.parseInt(runwayFields2.get(8).getText()), Integer.parseInt(runwayFields2.get(9).getText())
                            , Integer.parseInt(runwayFields2.get(10).getText()), Integer.parseInt(runwayFields2.get(11).getText()));

                    PhysicalRunway physicalRunway = new PhysicalRunway(runway1, runway2);
                    physicalRunways.add(physicalRunway);
                }

                PhysicalRunway[] physicalRunwaysArray = new PhysicalRunway[physicalRunways.size()];

                var airport = new Airport(view.getNameInput().getText(), physicalRunways.toArray(physicalRunwaysArray));
                model.airportListProperty().add(airport);

                view.getEditRunwaysVBox().getChildren().clear();
                view.getNameInput().clear();
                view.getEditAirportsVBox().getChildren().add(newRunway);
                model.addAction("Airport: '" + airport.getName() + "' was created");
            } catch (Exception ex) {
                view.getSaveAirport().setStyle("-fx-background-color: red");
                PauseTransition pauseTransition = new PauseTransition(
                        Duration.seconds(1)
                );
                pauseTransition.setOnFinished(tr -> {
                    view.getSaveAirport().setStyle(null);
                });
                pauseTransition.play();
            }
        });

    }

    public void newPhysicalRunway() {
        VBox physicalRunway = new VBox();
        physicalRunway.setSpacing(4);

        Label runwayLabel = new Label("Runway");
        runwayLabel.getStyleClass().add("settingsHeader");

        HBox removeHBox = new HBox();
        Region removeRegion = new Region();
        Button remove = new Button("Remove Runway");
        removeHBox.getChildren().addAll(removeRegion, remove);
        HBox.setHgrow(removeRegion, Priority.ALWAYS);

        var runway1 = newRunway();
        var runway2 = newRunway();

        var runwayPair = new Pair<>(runway1.getValue(), runway2.getValue());
        fields.add(runwayPair);

        physicalRunway.getChildren().addAll(runwayLabel, runway1.getKey(), runway2.getKey());
        view.getEditRunwaysVBox().getChildren().add(physicalRunway);

        remove.setOnAction(e -> {
            view.getEditRunwaysVBox().getChildren().remove(physicalRunway);
            fields.remove(runwayPair);
        });
    }

    public Pair<Node,ArrayList<TextField>> newRunway() {
        Label runwayNameLabel = new Label("THR Name: ");
        TextField runwayName = new TextField();
        HBox runwayNameHbox = new HBox();
        runwayNameHbox.getChildren().addAll(runwayNameLabel, runwayName);

        Label runwayLengthLabel = new Label("Length: ");
        TextField runwayLength = new TextField();
        HBox runwayLengthHBox = new HBox();
        runwayLengthHBox.getChildren().addAll(runwayLengthLabel, addMetre(runwayLength));

        Label runwayWidthLabel = new Label("Width: ");
        TextField runwayWidth = new TextField();
        HBox runwayWidthHBox = new HBox();
        runwayWidthHBox.getChildren().addAll(runwayWidthLabel, addMetre(runwayWidth));

        Label runwayBearingLabel = new Label("Bearing: ");
        TextField runwayBearing = new TextField();
        runwayBearing.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d*(\\.\\d*)?")) runwayBearing.setText(s);
        });
        HBox runwayBearingHBox = new HBox();
        runwayBearingHBox.getChildren().addAll(runwayBearingLabel, addDegree(runwayBearing));

        Label runwayTORALabel = new Label("TORA: ");
        TextField runwayTORA = new TextField();
        HBox runwayTORAHBox = new HBox();
        runwayTORAHBox.getChildren().addAll(runwayTORALabel, addMetre(runwayTORA));

        Label runwayTODALabel = new Label("TODA: ");
        TextField runwayTODA = new TextField();
        HBox runwayTODAHBox = new HBox();
        runwayTODAHBox.getChildren().addAll(runwayTODALabel, addMetre(runwayTODA));

        Label runwayASDALabel = new Label("ASDA: ");
        TextField runwayASDA= new TextField();
        HBox runwayASDAHBox = new HBox();
        runwayASDAHBox.getChildren().addAll(runwayASDALabel, addMetre(runwayASDA));

        Label runwayLDALabel = new Label("LDA: ");
        TextField runwayLDA = new TextField();
        HBox runwayLDAHBox = new HBox();
        runwayLDAHBox.getChildren().addAll(runwayLDALabel, addMetre(runwayLDA));

        Label runwayDispLabel = new Label("Displaced Thresh: ");
        TextField runwayDisp = new TextField();
        HBox runwayDispHBox = new HBox();
        runwayDispHBox.getChildren().addAll(runwayDispLabel, addMetre(runwayDisp));

        Label runwayClearLabel = new Label("Clearway Length: ");
        TextField runwayClear = new TextField();
        HBox runwayClearHBox = new HBox();
        runwayClearHBox.getChildren().addAll(runwayClearLabel, addMetre(runwayClear));

        Label runwayStopLengthLabel = new Label("Stopway Length: ");
        TextField runwayStopLength = new TextField();
        HBox runwayStopLengthHBox = new HBox();
        runwayStopLengthHBox.getChildren().addAll(runwayStopLengthLabel, addMetre(runwayStopLength));

        Label runwayStopWidthLabel = new Label("Stopway Width: ");
        TextField runwayStopWidth = new TextField();
        HBox runwayStopWidthHBox = new HBox();
        runwayStopWidthHBox.getChildren().addAll(runwayStopWidthLabel, addMetre(runwayStopWidth));

        VBox runway = new VBox();
        runway.setSpacing(4);


        Label runwayLabel = new Label("THR");
        runwayLabel.setStyle("-fx-font-weight: bold");

        runway.getChildren().addAll(runwayLabel,runwayNameHbox, runwayLengthHBox, runwayWidthHBox,
                runwayBearingHBox, runwayTORAHBox, runwayTODAHBox, runwayASDAHBox, runwayLDAHBox, runwayDispHBox,
                runwayClearHBox, runwayStopLengthHBox, runwayStopWidthHBox);

        ArrayList<TextField> rNodes = new ArrayList<>(Arrays.asList(runwayName, runwayLength, runwayWidth, runwayBearing
                ,runwayTORA, runwayTODA, runwayASDA, runwayLDA,
                runwayDisp, runwayClear, runwayStopLength, runwayStopWidth));

        ArrayList<TextField> listenerToAdd = new ArrayList<>();
        listenerToAdd.addAll(rNodes.subList(1,2));
        listenerToAdd.addAll(rNodes.subList(4,11));
        for (var node : listenerToAdd) {
            node.textProperty().addListener((observableValue, s, t1) -> {
                if (!t1.matches("[0-9]*")) node.setText(s);
            });
        }

        return new Pair<>(runway,rNodes);
    }

    public void loadAirports() {
        view.getAirportList().getChildren().clear();
        for (Airport airport : model.airportListProperty().get()) {
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(8);

            Label airportName = new Label(airport.getName());
            airportName.setPrefWidth(300);
            airportName.setMaxWidth(300);

            //Button editAirport = new Button("Edit");
            Button removeAirport = new Button("Delete");

            hbox.getChildren().addAll(airportName, removeAirport);
            view.getAirportList().getChildren().add(hbox);

            removeAirport.setOnAction(e -> {
                model.airportListProperty().remove(airport);
                view.getAirportList().getChildren().remove(hbox);
                model.addAction("Airport: '" + airport.getName() + "' was removed");
            });
        }
    }

    public Node addMetre(Node input) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        javafx.scene.text.Text metre = new Text("m");
        metre.getStyleClass().add("metre");

        hbox.getChildren().addAll(input, metre);
        return hbox;
    }

    public Node addDegree(Node input) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        javafx.scene.text.Text metre = new Text("°  ");
        metre.getStyleClass().add("metre");

        hbox.getChildren().addAll(input, metre);
        return hbox;
    }

    public void loadSettings() {
        SettingsView settingsView = new SettingsView();
        SettingsController settingsController = new SettingsController(settingsView, model);
        view.getView().getScene().setRoot(settingsView.getView());
    }
}
