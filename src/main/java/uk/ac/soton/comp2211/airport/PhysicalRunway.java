package uk.ac.soton.comp2211.airport;

import javafx.util.Pair;

public class PhysicalRunway {

    private Runway runwayOne;
    private Runway runwayTwo;

    public PhysicalRunway(Runway one, Runway two) {
        runwayOne = one;
        runwayTwo = two;
    }

    public String getName() {
        return runwayOne.getName() + "/" + runwayTwo.getName();
    }


    public Runway[] getRunways() {
        return new Runway[]{runwayOne, runwayTwo};
    }


}
