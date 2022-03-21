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
import uk.ac.soton.comp2211.airport.*;
import uk.ac.soton.comp2211.views.SelectionView;

public class AirportModel {

    //Airport
    private final SimpleListProperty<Airport> airportList = new SimpleListProperty<>();
    private SimpleObjectProperty<Airport> selectedAirport = new SimpleObjectProperty<>();
    private final SimpleListProperty<Obstacle> preDefinedObstacles = new SimpleListProperty<>();
    private SimpleObjectProperty<RedeclaredRunway> redeclaredRunway = new SimpleObjectProperty<>();
    private SimpleObjectProperty<State> state = new SimpleObjectProperty<>();


    public AirportModel() {}

    public AirportModel(Airport[] airportList) {
        this.airportList.setValue(FXCollections.observableArrayList(airportList));
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

}
