package uk.ac.soton.comp2211.airport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleOnRunwayTest {
     static ObstacleOnRunway obstacleOnRunway;
    @BeforeAll
    static void initialiseObstalce() {

       Obstacle obstacle = new Obstacle("Test Obstacle",10,11,12 );
       obstacleOnRunway = new ObstacleOnRunway(obstacle,13,14);




    }

    @Test
    void getPosition() {
        assertEquals(obstacleOnRunway.getPosition()==13,"Error with getPosition function");
    }

    @Test
    void setPosition() {
        obstacleOnRunway.setPosition(130);
        assertEquals(obstacleOnRunway.getPosition()==130,"Error with setPosition function");
    }

    @Test
    void getDFCL() {
        assertEquals(obstacleOnRunway.getDFCL()==14,"Error with getDFCL function");
    }

    @Test
    void setDFCL() {
        obstacleOnRunway.setDFCL(140);
        assertEquals(obstacleOnRunway.getDFCL()==140,"Error with getDFCL function");
    }
}