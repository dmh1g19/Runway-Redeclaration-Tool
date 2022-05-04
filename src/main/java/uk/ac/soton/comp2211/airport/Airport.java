package uk.ac.soton.comp2211.airport;

public class Airport {
    protected String name;
    protected PhysicalRunway[] runways;

    public Airport() {

    }

    public Airport(String name, PhysicalRunway[] runways) {
        this.name = name;
        this.runways = runways;
    }

    public String getName() {
        return name;
    }

    public PhysicalRunway[] getRunways() {
        return runways;
    }

    public void setRunways(PhysicalRunway[] runways) {
        this.runways = runways;
    }
}
