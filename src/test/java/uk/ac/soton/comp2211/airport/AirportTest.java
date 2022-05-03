package uk.ac.soton.comp2211.airport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AirportTest {
    private  static Airport airport;
    private static Runway[] runways;
    @BeforeAll
    static void initialiseAirport() {

        runways = new Runway[4];
        runways[0] = new Runway("09R", 4022, 280, 90, 3660, 3660, 3660, 3353, 5);
        runways[0].setDisplacedThreshold(307);
        runways[1] = new Runway("27L", 4022, 280, 270, 3660, 3660, 3660, 3660, 5);
        runways[2] = new Runway("09L", 4022, 280, 90, 3902, 3902, 3902, 3595, 5);
        runways[2].setDisplacedThreshold(306);
        runways[3] = new Runway("27R", 4022, 280, 270, 3884, 3962, 3884, 3884, 5);
        PhysicalRunway[] physicalRunways = new PhysicalRunway[2];
        physicalRunways[0] = new PhysicalRunway(runways[0],runways[1]);
        physicalRunways[1] = new PhysicalRunway(runways[2],runways[3]);

        airport = new Airport("Heathrow",physicalRunways);



    }

    @Test
    void getName() {
        Assertions.assertTrue(airport.getName().equals("Heathrow"),"Error with Airport getname method");
    }

    @Test
    void getRunways() {
        PhysicalRunway[] physicalRunways = airport.getRunways();
        Assertions.assertTrue(physicalRunways[0].getFirst()==runways[0],"Error with Airport getRunways method (runway 0)");
        Assertions.assertTrue(physicalRunways[0].getSecond()==runways[1],"Error with Airport getRunways method (runway 1)");
        Assertions.assertTrue(physicalRunways[1].getFirst()==runways[2],"Error with Airport getRunways method (runway 2)");
        Assertions.assertTrue(physicalRunways[1].getSecond()==runways[3],"Error with Airport getRunways method (runway 3)");
    }

    @Test
    void setRunways() {
       Runway[] runways = new Runway[4];
       runways[0] = new Runway("Runway1", 3000, 200, 100, 2660, 2660, 2660, 2353, 5);
       runways[0].setDisplacedThreshold(307);
       runways[1] = new Runway("Runway2", 4000, 300, 260, 3660, 3660, 3660, 3660, 5);
       PhysicalRunway[] physicalRunways = new  PhysicalRunway[1];
       physicalRunways[0]= new PhysicalRunway(runways[0],runways[1]);
       airport.setRunways(physicalRunways);
       Assertions.assertTrue(physicalRunways[0].getFirst()==runways[0],"Error with Airport setRunways method (runway 0)");
       Assertions.assertTrue(physicalRunways[0].getSecond()==runways[1],"Error with Airport setRunways method (runway 1)");

    }
}