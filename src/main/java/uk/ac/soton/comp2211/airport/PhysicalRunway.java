package uk.ac.soton.comp2211.airport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;



public class PhysicalRunway {

    private Runway first;
    private Runway second;


    public PhysicalRunway(Runway f, Runway s) {
        Runway[] runways = new Runway[]{f, s};
        Arrays.sort(runways, Comparator.comparing(Runway::getName));
        this.first = runways[0];
        this.second = runways[1];
    }

    public String getName() {
        return first.getName() + "/" + second.getName();
    }

    public Runway getFirst() {
        return first;
    }

    public Runway getSecond() {
        return second;
    }

    public Runway[] getRunways() {
        return new Runway[]{first, second};
    }


    public String getLowerThreshold() {
        return first.getName();
    }

    public String getUpperThreshold() {
        return second.getName();
    }


}
