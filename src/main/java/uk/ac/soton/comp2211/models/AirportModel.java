package uk.ac.soton.comp2211.models;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.utility.Calculator;
import uk.ac.soton.comp2211.utility.XMLUtil;
import uk.ac.soton.comp2211.views.SelectionView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    private final SimpleListProperty<Pair<Date,Object>> actions = new SimpleListProperty<>();
    private final SimpleIntegerProperty blastProtection = new SimpleIntegerProperty(300);


    public AirportModel() {}

    public AirportModel(Airport[] airportList) {
        this.airportList.setValue(FXCollections.observableArrayList(airportList));
        blastProtection.addListener((observableValue, number, t1) -> Calculator.setBlastProtectionDistance((Integer) t1));
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
        XMLUtil.writeObstacles(new ArrayList<>(preDefinedObstacles));
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

    public ObservableList<Pair<Date,Object>> getActions() {
        return actions.get();
    }

    public SimpleListProperty<Pair<Date,Object>> actionsProperty() {
        return actions;
    }

    public void addAction(Date date,String action) {
        this.actions.add(new Pair<>(date,action));
    }

    public int getBlastProtection() {
        return blastProtection.get();
    }

    public SimpleIntegerProperty blastProtectionProperty() {
        return blastProtection;
    }
}
