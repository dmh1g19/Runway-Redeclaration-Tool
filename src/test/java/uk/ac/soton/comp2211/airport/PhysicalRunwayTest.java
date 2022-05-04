package uk.ac.soton.comp2211.airport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicalRunwayTest {


    private  static PhysicalRunway[] physicalRunways;
    private static Runway[] runways;
    @BeforeEach
     void initialiseAirport() {

        runways = new Runway[4];
        runways[0] = new Runway("09R", 4022, 280, 90, 3660, 3660, 3660, 3353, 5);
        runways[0].setDisplacedThreshold(307);
        runways[1] = new Runway("27L", 4022, 280, 270, 3660, 3660, 3660, 3660, 5);
        runways[2] = new Runway("09L", 4022, 280, 90, 3902, 3902, 3902, 3595, 5);
        runways[2].setDisplacedThreshold(306);
        runways[3] = new Runway("27R", 4022, 280, 270, 3884, 3962, 3884, 3884, 5);
        physicalRunways = new PhysicalRunway[2];
        physicalRunways[0] = new PhysicalRunway(runways[0],runways[1]);
        physicalRunways[1] = new PhysicalRunway(runways[2],runways[3]);




    }

    @Test
    void getName() {

        assertTrue(physicalRunways[0].getName().equals("09R/27L"),"Error with physical runway getName function");
        assertTrue(physicalRunways[1].getName().equals("09L/27R"),"Error with physical runway getName function");

    }

    @Test
    void getFirst() {
        assertTrue(physicalRunways[0].getFirst() == runways[0],"Error with physical runway getFirst function");
        assertTrue(physicalRunways[1].getFirst() == runways[2],"Error with physical runway getFirst function");

    }

    @Test
    void getSecond() {
        assertTrue(physicalRunways[0].getSecond() == runways[1],"Error with physical runway getFirst function");
        assertTrue(physicalRunways[1].getSecond() == runways[3],"Error with physical runway getFirst function");
    }

    @Test
    void getRunways() {
        assertTrue(physicalRunways[0].getRunways()[0] == runways[0],"Error with physical runway getRunways function");
        assertTrue(physicalRunways[0].getRunways()[1] == runways[1],"Error with physical runway getRunways function");
        assertTrue(physicalRunways[1].getRunways()[0] == runways[2],"Error with physical runway getRunways function");
        assertTrue(physicalRunways[1].getRunways()[1] == runways[3],"Error with physical runway getRunways function");
    }

    @Test
    void getLowerThreshold() {
        assertTrue(physicalRunways[0].getLowerThreshold().equals(runways[0].getName()),"Error with physical runway getLowerThreshold function");
        assertTrue(physicalRunways[1].getLowerThreshold().equals(runways[2].getName()),"Error with physical runway getLowerThreshold function");
    }

    @Test
    void getUpperThreshold() {
        assertTrue(physicalRunways[0].getUpperThreshold().equals(runways[1].getName()),"Error with physical runway getUpperThreshold function");
        assertTrue(physicalRunways[1].getUpperThreshold().equals(runways[3].getName()),"Error with physical runway getUpperThreshold function");
    }

    @Test
    void PhysicalRunway(){
        boolean failed = true;
        try {
           PhysicalRunway physicalRunway = new PhysicalRunway();
           failed = false;
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
        assertFalse(failed, "Error with physcial runway base constructor");
    }
}