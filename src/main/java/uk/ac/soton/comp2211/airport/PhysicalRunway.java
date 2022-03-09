package uk.ac.soton.comp2211.airport;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;

public class PhysicalRunway {

    private ArrayList<Runway> runways;

    public PhysicalRunway(ArrayList<Runway> runways) {
        this.runways = runways;
    }

    public String getName() {
        HashSet<String> names = new HashSet<>();
        for (Runway runway : runways) {
            names.add(runway.getName());
        }
        String[] name = names.toArray(String[]::new);
        return String.join("/",name);
    }


    public Runway[] getRunways() {
        return runways.toArray(Runway[]::new);
    }


}
