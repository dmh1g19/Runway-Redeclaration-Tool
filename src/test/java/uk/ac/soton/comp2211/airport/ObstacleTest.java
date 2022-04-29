package uk.ac.soton.comp2211.airport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {
    static Obstacle obstacle;
    @BeforeAll
    static void initialiseObstalce() {
         obstacle = new Obstacle("Test Obstacle",10,11,12 );



    }
    @Test
    void getLength() {
        assertTrue(obstacle.getLength()==10,"Error with getting obstacle ");
    }

    @Test
    void setLength() {
        obstacle.setLength(100);
        assertTrue(obstacle.getLength()==100,"Error with setting obstacle length");
    }

    @Test
    void getName() {
        assertTrue(obstacle.getName().equals("Test Obstacle"), "Error with getting obstacle name ");
    }


    @Test
    void setName() {
        obstacle.setName("New Name");
        assertTrue(obstacle.getName().equals("New Name"), "Error with setting obstacle name ");
    }

    @Test
    void getHeight() {
        assertTrue(obstacle.getHeight()==11,"Error with getting obstacle height");
    }


    @Test
    void setHeight() {
        obstacle.setHeight(110);
        assertTrue(obstacle.getHeight()==110,"Error with setting obstacle height");
    }

    @Test
    void getWidth() {
        assertTrue(obstacle.getHeight()==12,"Error with getting obstacle width");
    }


    @Test
    void setWidth() {
        obstacle.setWidth(120);
        assertTrue(obstacle.getHeight()==120,"Error with setting obstacle width");
    }

}