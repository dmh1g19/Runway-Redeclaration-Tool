package uk.ac.soton.comp2211.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.utility.Calculator;
import uk.ac.soton.comp2211.utility.XMLUtil;

import java.io.File;
import java.util.*;

public class AirportModel {

    //Airport
    private final SimpleListProperty<Airport> airportList = new SimpleListProperty<>();
    private SimpleObjectProperty<Airport> selectedAirport = new SimpleObjectProperty<>();
    private final SimpleListProperty<Obstacle> preDefinedObstacles = new SimpleListProperty<>();
    private SimpleObjectProperty<RedeclaredRunway> redeclaredRunway = new SimpleObjectProperty<>();
    private SimpleObjectProperty<State> state = new SimpleObjectProperty<>();
    private final SimpleIntegerProperty fontSize = new SimpleIntegerProperty(1);
    private final SimpleBooleanProperty highContrast = new SimpleBooleanProperty(false);
    private boolean isCustomColours = false;
    private final SimpleStringProperty customColours = new SimpleStringProperty("");
    private final SimpleObjectProperty<Pair<RedeclaredRunway,RedeclaredRunway>> redeclaredRunways = new SimpleObjectProperty<>();
    private final SimpleListProperty<Pair<Date,String>> actions = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final SimpleIntegerProperty blastProtection = new SimpleIntegerProperty(300);

    public SimpleIntegerProperty fontSizeProperty() {
        return fontSize;
    }

    public AirportModel() {
        initialiseNotifications();
    }

    public AirportModel(Airport[] airportList) {
        this.airportList.setValue(FXCollections.observableArrayList(airportList));
        blastProtection.addListener((observableValue, number, t1) -> Calculator.setBlastProtectionDistance((Integer) t1));
        initialiseNotifications();
    }

    private void initialiseNotifications() {
        // LISTEN FOR ACTIONS
        selectedAirportProperty().addListener((observableValue, airport, t1) -> {
            addAction(t1.getName() + " Selected");
        });

        redeclaredRunwaysProperty().addListener((observableValue, redeclaredRunwayRedeclaredRunwayPair, t1) -> {
            addAction("New values calculated for runway: " + t1.getKey().getRunway().getName() + "/" + t1.getValue().getRunway().getName());
        });

        blastProtectionProperty().addListener((observableValue, number, t1) -> {
            addAction("Blast protection distance changed to: " + t1 + "m");
        });

        customColoursProperty().addListener((observableValue, s, t1) -> {
            if (t1 == null) return;
            addAction("App Colours Changed");
        });

        fontSizeProperty().addListener((observableValue, number, t1) -> {
            if (t1.equals(0)) addAction("Font size set to Small");
            if (t1.equals(1)) addAction("Font size set to Medium");
            if (t1.equals(2)) addAction("Font size set to Large");
        });
    }

    public Pair<Runway,State>[] getRunwayStates(PhysicalRunway physicalRunway) {
        Pair<Runway,State>[] runwayStates = new Pair[4];
        runwayStates[0] = new Pair<>(physicalRunway.getFirst(), State.LANDING);
        runwayStates[1] = new Pair<>(physicalRunway.getFirst(), State.TAKEOFF);
        runwayStates[2] = new Pair<>(physicalRunway.getSecond(), State.LANDING);
        runwayStates[3] = new Pair<>(physicalRunway.getSecond(), State.TAKEOFF);
        return runwayStates;
    }

    public void writeObstaclesToXML() {
        try {
            XMLUtil.exportObstacles(new File("obstacles.xml"),new ArrayList<>(preDefinedObstacles));
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }


    }

    public void setRedeclaredRunway(RedeclaredRunway redeclaredRunway) {
        this.redeclaredRunway.set(redeclaredRunway);
    }

    public void setState(State state) {
        this.state.set(state);
    }

    public void setAirportList(Airport[] airportList) {
        this.airportList.setValue(FXCollections.observableArrayList(airportList));
    }

    public void setPreDefinedObstacles(Obstacle[] preDefinedObstacles) {
        this.preDefinedObstacles.set(FXCollections.observableArrayList(preDefinedObstacles));
    }

    public SimpleListProperty<Airport> airportListProperty() {
        return airportList;
    }

    public SimpleObjectProperty<Airport> selectedAirportProperty() {
        return selectedAirport;
    }

    public SimpleListProperty<Obstacle> preDefinedObstaclesProperty() {
        return preDefinedObstacles;
    }

    public SimpleObjectProperty<RedeclaredRunway> redeclaredRunwayProperty() {
        return redeclaredRunway;
    }

    public SimpleObjectProperty<State> stateProperty() {
        return state;
    }

    public SimpleObjectProperty<Pair<RedeclaredRunway, RedeclaredRunway>> redeclaredRunwaysProperty() {
        return redeclaredRunways;
    }

    public int getFontSize() {
        return fontSize.get();
    }
    public void setFontSize(int i){
        fontSize.set(i);
    }

    public boolean isHighContrast() {
        return highContrast.get();
    }

    public void setHighContrast(boolean contrast) {
         highContrast.set(contrast);
    }

    public SimpleBooleanProperty highContrastProperty() {
        return highContrast;
    }

    public String getCustomColours() {
        return customColours.get();
    }


    public void setCustomColours(String customColours) {
        this.customColours.set(customColours);
    }

    public boolean isCustomColours() {
        return isCustomColours;
    }

    public void setHasCustomColours(boolean customColours) {
        isCustomColours = customColours;
    }

    public ObservableList<Pair<Date,String>> getActions() {
        return actions.get();
    }

    public SimpleListProperty<Pair<Date,String>> actionsProperty() {
        return actions;
    }

    public void addAction(String action) {
        this.actions.add(new Pair<Date,String>(new Date(),action));
    }


    public int getBlastProtection() {
        return blastProtection.get();
    }

    public SimpleIntegerProperty blastProtectionProperty() {
        return blastProtection;
    }

    public SimpleStringProperty customColoursProperty() {
        return customColours;
    }
}
