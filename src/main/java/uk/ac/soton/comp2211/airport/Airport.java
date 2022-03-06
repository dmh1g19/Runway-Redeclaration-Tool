package uk.ac.soton.comp2211.airport;

public class Airport {
    protected String name;
    protected Runway[] runways;

    public Airport(String name, Runway[] runways) {
        this.name = name;
        this.runways = runways;
    }

    public String getName() {
        return name;
    }

    public Runway[] getRunways() {
        return runways;
    }

    public void setRunways(Runway[] runways) {
        this.runways = runways;
    }
}
