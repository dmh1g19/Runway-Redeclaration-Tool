package uk.ac.soton.comp2211.components;

public class Airport {
    protected String name;
    protected Runway[] runways;

    public Airport(String name, Runway[] runways) {
        this.name = name;
        this.runways = runways;
    }
}
