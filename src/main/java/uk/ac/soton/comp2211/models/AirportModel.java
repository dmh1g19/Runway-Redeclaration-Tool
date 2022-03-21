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
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.Obstacle;
import uk.ac.soton.comp2211.airport.PhysicalRunway;
import uk.ac.soton.comp2211.views.SelectionView;

public class AirportModel {

    private final SimpleListProperty<Airport> airportList = new SimpleListProperty<>();
    private SimpleObjectProperty<Airport> selectedAirport = new SimpleObjectProperty<>();
    private final SimpleListProperty<Obstacle> preDefinedObstacles = new SimpleListProperty<>();

    public AirportModel() {

    }

    public AirportModel(Airport[] airportList) {
        this.airportList.setValue(FXCollections.observableArrayList(airportList));
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

}
