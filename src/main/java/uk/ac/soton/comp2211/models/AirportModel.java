package uk.ac.soton.comp2211.models;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.utility.XMLUtil;
import uk.ac.soton.comp2211.views.SelectionView;

import java.util.ArrayList;
import java.util.HashMap;

public class AirportModel {

    //Airport
    private final SimpleListProperty<Airport> airportList = new SimpleListProperty<>();
    private SimpleObjectProperty<Airport> selectedAirport = new SimpleObjectProperty<>();
    private final SimpleListProperty<Obstacle> preDefinedObstacles = new SimpleListProperty<>();
    private SimpleObjectProperty<RedeclaredRunway> redeclaredRunway = new SimpleObjectProperty<>();
    private SimpleObjectProperty<State> state = new SimpleObjectProperty<>();

    private SimpleObjectProperty<Pair<RedeclaredRunway,RedeclaredRunway>> redeclaredRunways = new SimpleObjectProperty<>();


    public AirportModel() {}

    public AirportModel(Airport[] airportList) {
        this.airportList.setValue(FXCollections.observableArrayList(airportList));
    }

    public Pair<Runway,State>[] getRunwayStates(PhysicalRunway physicalRunway) {
        Pair<Runway,State>[] runwayStates = new Pair[4];
        runwayStates[0] = new Pair(physicalRunway.getFirst(), State.LANDING);
        runwayStates[1] = new Pair(physicalRunway.getFirst(), State.TAKEOFF);
        runwayStates[2] = new Pair(physicalRunway.getSecond(), State.LANDING);
        runwayStates[3] = new Pair(physicalRunway.getSecond(), State.TAKEOFF);
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

}
