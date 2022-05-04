package uk.ac.soton.comp2211.airport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedeclaredRunwayTest {

    static ObstacleOnRunway obstacleOnRunway;
    static  Runway runway;
    static  RedeclaredRunway redeclaredRunway;
    @BeforeEach
     void initialiseRunway() {

        Obstacle obstacle = new Obstacle("Test Obstacle",10,11,12 );
        obstacleOnRunway = new ObstacleOnRunway(obstacle,13,14);
        runway = new Runway("09R", 4022, 280, 90, 3660, 3660, 3660, 3353, 5);
        redeclaredRunway = new RedeclaredRunway(runway,obstacleOnRunway,Direction.TOWARDS);




    }

    @Test
    void getRunway() {
        assertTrue(redeclaredRunway.getRunway()==runway,"Error with redeclared runway getRunway function");
    }

    @Test
    void getObstacle() {
        assertTrue(redeclaredRunway.getObstacle()==obstacleOnRunway,"Error with redeclared runway getObstacle function");

    }

    @Test
    void getDirection() {
        assertTrue(redeclaredRunway.getDirection()==Direction.TOWARDS,"Error with redeclared runway getDirection function");
    }
}