package uk.ac.soton.comp2211.utility;

import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    static Airport heathrow;

    @BeforeAll
    static void initialiseAirport() {
        Runway[] runways  = new Runway[4];

        runways[0] = new Runway("09L",4022,280,90,3902,3902,3902,3595,new Pair<>(0,0));
        runways[1]= new Runway("27R",4022,280,270,3884,3962,3884,3884,new Pair<>(0,0));
        runways[2]= new Runway("09R",4022,280,90,3660,3660,3660,3253,new Pair<>(0,0));
        runways[3] = new Runway("27R",4022,280,270,3660,3660,3660,3253,new Pair<>(0,0));
        heathrow = new Airport("heathrow",runways);


    }

    @Test
    void exampleCalculation1 (){
        Runway[] runways = heathrow.getRunways();
        ObstacleOnRunway obs = new ObstacleOnRunway("test" , 12,0,-50,0 );
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test" , 12,0,3646,0 );
        Runway runway1 = Calculator.TakeOffAwayFromObstacle(runways[0],obs);
        runway1 = Calculator.LandingOverObstacle(runway1,obs);
        Runway runway2 = Calculator.TakeOffTowardsObstacle(runways[1],obs2);
        runway2 = Calculator.LandingTowardsObstacle(runway2,obs2);
        Assertions.assertTrue(3346== runway1.getTORA(),"Runway TORA Incorrect");
        Assertions.assertTrue(3346== runway1.getASDA(),"Runway ASDA Incorrect");
        Assertions.assertTrue(3346== runway1.getTODA(),"Runway TODA Incorrect");
        Assertions.assertTrue(2985== runway1.getLDA(),"Runway TORA Incorrect");
    }


}