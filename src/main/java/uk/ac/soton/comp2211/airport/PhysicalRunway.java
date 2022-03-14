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

    public PhysicalRunway() {

    }

    public PhysicalRunway(Runway f, Runway s) {
        this.first = f;
        this.second = s;
    }

    public String getName() {
        return first.getName() + "/" + second.getName();
    }

    public void setFirst(Runway f) {
        this.first = f;
    }

    public void setSecond(Runway s) {
        this.second = s;
    }


    public Runway getFirst() {
        return first;
    }

    public Runway getSecond() {
        return second;
    }

    public Runway[] getRunways() {
        Runway[] runways = new Runway[]{first, second};
        Arrays.sort(runways, Comparator.comparing(Runway::getName));
        return runways;
    }


    public String getLowerThreshold() {
        if (first.getName().compareTo(second.getName()) <= 0) {
            return first.getName();
        }
        return second.getName();
    }

}
