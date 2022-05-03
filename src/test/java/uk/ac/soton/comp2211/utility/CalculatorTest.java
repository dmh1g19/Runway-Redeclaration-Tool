package uk.ac.soton.comp2211.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.soton.comp2211.airport.Direction;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    static Runway[] runways;

    @BeforeAll
    static void initialiseAirport() {
        runways = new Runway[4];
        runways[0] = new Runway("09R", 4022, 280, 90, 3660, 3660, 3660, 3353, 5);
        runways[0].setDisplacedThreshold(307);
        runways[1] = new Runway("27L", 4022, 280, 270, 3660, 3660, 3660, 3660, 5);
        runways[2] = new Runway("09L", 4022, 280, 90, 3902, 3902, 3902, 3595, 5);
        runways[2].setDisplacedThreshold(306);
        runways[3] = new Runway("27R", 4022, 280, 270, 3884, 3962, 3884, 3884, 5);


    }

    /**
     * Scenario 1
     * 12m tall obstacle, on the centreline, 50m before the 09L threshold, i.e. to the west of the
     * threshold. The same obstacle is 3646m from the 27R threshold.
     */
    @Test
    void exampleCalculation1() throws Calculator.IncorrectObstacleException {

        ObstacleOnRunway obs = new ObstacleOnRunway("test", 12, 0, 0, -50, 0);
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 12, 0, 0, 3646, 0);
        obs.setPosition(obs.getPosition()+runways[2].getDTL());
        Runway runway1 = Calculator.TakeOffAwayFromObstacle(runways[2], obs);

        runway1 = Calculator.LandingOverObstacle(runway1, obs);
        Runway runway2 = Calculator.TakeOffTowardsObstacle(runways[3], obs2);
        runway2 = Calculator.LandingTowardsObstacle(runway2, obs2);
        Assertions.assertTrue(3346 == runway1.getASDA(), "09L ASDA Incorrect " + (runway1.getASDA()));
        Assertions.assertTrue(3346 == runway1.getTODA(), "09L TODA Incorrect " + (runway1.getTODA()));
        Assertions.assertTrue(2985 == runway1.getLDA(), "09L LDA Incorrect " + runway1.getLDA());

        Assertions.assertTrue(2986 == runway2.getTORA(), "27R TORA Incorrect " + (runway2.getTORA()));
        Assertions.assertTrue(2986 == runway2.getASDA(), "27R ASDA Incorrect " + (runway2.getASDA()));
        Assertions.assertTrue(2986 == runway2.getTODA(), "27R TODA Incorrect " + (runway2.getTODA()));
        Assertions.assertTrue(3346 == runway2.getLDA(), "27R LDA Incorrect " + runway2.getLDA());
    }

    /**
     * Scenario 2
     * 25m tall obstacle, 20m south of the centerline, 500m from the 27L threshold and 2853m from
     * 09R threshold.
     */
    @Test
    void exampleCalculation2() throws Calculator.IncorrectObstacleException {

        ObstacleOnRunway obs = new ObstacleOnRunway("test", 25, 0, 0, 500, 0);
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 25, 0, 0,2853, 0);
        Runway runway1 = Calculator.TakeOffAwayFromObstacle(runways[1], obs);
        runway1 = Calculator.LandingOverObstacle(runway1, obs);
        obs2.setPosition(obs2.getPosition()+runways[0].getDTL());
        Runway runway2 = Calculator.TakeOffTowardsObstacle(runways[0], obs2);

        runway2 = Calculator.LandingTowardsObstacle(runway2, obs2);
        //09R
        Assertions.assertTrue(1850 == runway2.getTORA(), "09R TORA Incorrect " + (runway2.getTORA()));
        Assertions.assertTrue(1850 == runway2.getASDA(), "09R ASDA Incorrect " + (runway2.getASDA()));
        Assertions.assertTrue(1850 == runway2.getTODA(), "09R TODA Incorrect " + (runway2.getTODA()));
        Assertions.assertTrue(2553 == runway2.getLDA(), "09R LDA Incorrect " + runway2.getLDA());

        //27L
        Assertions.assertTrue(2860 == runway1.getTORA(), "27L TORA Incorrect " + (runway1.getTORA()));
        Assertions.assertTrue(2860 == runway1.getASDA(), "27L ASDA Incorrect " + (runway1.getASDA()));
        Assertions.assertTrue(2860 == runway1.getTODA(), "27L TODA Incorrect " + (runway1.getTODA()));
        Assertions.assertTrue(1850 == runway1.getLDA(), "27L LDA Incorrect " + runway1.getLDA());


    }


    /**
     * Scenario 3
     * 15m tall obstacle, 60m north of centreline, 150m from 09R threshold and 3203m from 27L
     * threshold.
     */
    @Test
    void exampleCalculation3() throws Calculator.IncorrectObstacleException {

        ObstacleOnRunway obs = new ObstacleOnRunway("test", 15, 0, 0, 150, 0);
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 15,0, 0, 3203, 0);
        obs.setPosition(obs.getPosition()+runways[0].getDTL());
        Runway runway1 = Calculator.TakeOffAwayFromObstacle(runways[0], obs);
        runway1 = Calculator.LandingOverObstacle(runway1, obs);

        Runway runway2 = Calculator.TakeOffTowardsObstacle(runways[1], obs2);
        runway2 = Calculator.LandingTowardsObstacle(runway2, obs2);
        Assertions.assertTrue(2903 == runway1.getTORA(), "09R TORA Incorrect " + (runway1.getTORA()));
        Assertions.assertTrue(2903 == runway1.getASDA(), "09R ASDA Incorrect " + (runway1.getASDA()));
        Assertions.assertTrue(2903 == runway1.getTODA(), "09R TODA Incorrect " + (runway1.getTODA()));
        Assertions.assertTrue(2393 == runway1.getLDA(), "09R LDA Incorrect " + runway1.getLDA());

        Assertions.assertTrue(2393 == runway2.getTORA(), "27L TORA Incorrect " + (runway2.getTORA()));
        Assertions.assertTrue(2393 == runway2.getASDA(), "27L ASDA Incorrect " + (runway2.getASDA()));
        Assertions.assertTrue(2393 == runway2.getTODA(), "27L TODA Incorrect " + (runway2.getTODA()));
        Assertions.assertTrue(2903 == runway2.getLDA(), "27L LDA Incorrect " + runway2.getLDA());
    }


    /**
     * Scenario 4
     * 20m tall obstacle, 20m right of centreline (to the north), 50m from 27R threshold and 3546m
     * from 09L threshold.
     */
    @Test
    void exampleCalculation4() throws Calculator.IncorrectObstacleException {

        ObstacleOnRunway obs = new ObstacleOnRunway("test", 20, 0,0, 50, 0);
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 20, 0, 0, 3546, 0);
        obs2.setPosition(obs2.getPosition()+runways[2].getDTL());
        Runway runway1 = Calculator.TakeOffAwayFromObstacle(runways[3], obs);
        runway1 = Calculator.LandingOverObstacle(runway1, obs);
        Runway runway2 = Calculator.TakeOffTowardsObstacle(runways[2], obs2);
        runway2 = Calculator.LandingTowardsObstacle(runway2, obs2);

        //09L
        Assertions.assertTrue(2792 == runway2.getTORA(), "09L TORA Incorrect " + (runway2.getTORA()));
        Assertions.assertTrue(2792 == runway2.getASDA(), "09L ASDA Incorrect " + (runway2.getASDA()));
        Assertions.assertTrue(2792 == runway2.getTODA(), "09L TODA Incorrect " + (runway2.getTODA()));
        Assertions.assertTrue(3246 == runway2.getLDA(), "09L LDA Incorrect " + runway2.getLDA());

        //27R
        Assertions.assertTrue(3534 == runway1.getTORA(), "27R TORA Incorrect " + (runway1.getTORA()));
        Assertions.assertTrue(3534 == runway1.getASDA(), "27R ASDA Incorrect " + (runway1.getASDA()));
        Assertions.assertTrue(3612 == runway1.getTODA(), "27R TODA Incorrect " + (runway1.getTODA()));
        Assertions.assertTrue(2774 == runway1.getLDA(), "27R LDA Incorrect " + runway1.getLDA());


    }

    @Test
    void awayFromObstacleTest() throws Calculator.IncorrectObstacleException {
        ObstacleOnRunway obs = new ObstacleOnRunway("test", 12, 0,0, -50, 0);
        obs.setPosition(obs.getPosition()+runways[2].getDTL());
        Runway runway1 = Calculator.AwayFromObstacle(runways[2],obs).getRunway();
        obs.setPosition(obs.getPosition()+runways[2].getDTL());
        Assertions.assertTrue(3346 == runway1.getTORA(), "09L TORA Incorrect " + (runway1.getTORA()));
        Assertions.assertTrue(3346 == runway1.getASDA(), "09L ASDA Incorrect " + (runway1.getASDA()));
        Assertions.assertTrue(3346 == runway1.getTODA(), "09L TODA Incorrect " + (runway1.getTODA()));
        Assertions.assertTrue(2985 == runway1.getLDA(), "09L LDA Incorrect " + runway1.getLDA());
    }

    @Test
    void towardsObstacleTest() throws Calculator.IncorrectObstacleException {
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 12, 0, 0,  3646,0);

        Runway runway2 = Calculator.TowardsObstacle(runways[3],obs2).getRunway();
        Assertions.assertTrue(2986 == runway2.getTORA(), "27R TORA Incorrect " + (runway2.getTORA()));
        Assertions.assertTrue(2986 == runway2.getASDA(), "27R ASDA Incorrect " + (runway2.getASDA()));
        Assertions.assertTrue(2986 == runway2.getTODA(), "27R TODA Incorrect " + (runway2.getTODA()));
        Assertions.assertTrue(3346 == runway2.getLDA(), "27R LDA Incorrect " + runway2.getLDA());
    }

    @Test
    void invalidHeightTest (){


        //testing negative height
        ObstacleOnRunway obs = new ObstacleOnRunway("test", -10, 0, 20, 0,0);
        Calculator.IncorrectObstacleException t;
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.AwayFromObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.AwayFromObstacle negative height error");
        t =assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TowardsObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.TowardsObstacle negative height error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TakeOffTowardsObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.TakeOffTowardsObstacle negative height error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TakeOffAwayFromObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.TakeOffAwayFromObstacle negative height error");
        t =assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.LandingOverObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.LandingOverObstacle negative height  error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.LandingTowardsObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.LandingTowardsObstacle negative height error");

        //testing large height
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 1000000, 0, 20, 0,0);
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.AwayFromObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.AwayFromObstacle large height error");
        t =assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TowardsObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.TowardsObstacle large height error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TakeOffTowardsObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.TakeOffTowardsObstacle large height error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TakeOffAwayFromObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.TakeOffAwayFromObstacle large height error");
        t =assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.LandingOverObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.LandingOverObstacle large height  error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.LandingTowardsObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Height of obstacle incorrect","Calculator.LandingTowardsObstacle large height error");


    }

    @Test
    void invalidPositionTest (){


        //testing negative position
        ObstacleOnRunway obs = new ObstacleOnRunway("test", 10, 0,0, -100000000,0);
        Calculator.IncorrectObstacleException t;
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.AwayFromObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.AwayFromObstacle negative position error");
        t =assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TowardsObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.TowardsObstacle negative position error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TakeOffTowardsObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.TakeOffTowardsObstacle negative position error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TakeOffAwayFromObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.TakeOffAwayFromObstacle negative position error");
        t =assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.LandingOverObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.LandingOverObstacle negative position  error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.LandingTowardsObstacle(runways[0],obs));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.LandingTowardsObstacle negative position error");

        //testing large position
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 10, 0,0, 100000000,0);
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.AwayFromObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.AwayFromObstacle large position error");
        t =assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TowardsObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.TowardsObstacle large position error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TakeOffTowardsObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.TakeOffTowardsObstacle large position error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.TakeOffAwayFromObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.TakeOffAwayFromObstacle large position error");
        t =assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.LandingOverObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.LandingOverObstacle large position  error");
        t = assertThrows( Calculator.IncorrectObstacleException.class, () -> Calculator.LandingTowardsObstacle(runways[0],obs2));
        assertEquals(t.getMessage(),"Position of obstacle incorrect","Calculator.LandingTowardsObstacle large position error");


    }


    @Test
    void towardsObstacleBreakdownTest() throws Calculator.IncorrectObstacleException {
        ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 12, 0, 0, 3646, 0);

        System.out.println( Calculator.calculationBreakdown(runways[3],obs2, Direction.TOWARDS));
        assertEquals(1,1);

    }


    @Test
    void awayFromObstacleBreakdownTest() throws Calculator.IncorrectObstacleException {
        ObstacleOnRunway obs = new ObstacleOnRunway("test", 12, 0, 0, -50, 0);
        System.out.println(Calculator.calculationBreakdown(runways[2],obs,Direction.AWAYOVER));
        assertEquals(1,1);

    }

}
