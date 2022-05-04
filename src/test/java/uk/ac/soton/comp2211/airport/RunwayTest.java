package uk.ac.soton.comp2211.airport;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunwayTest {
    static Runway runway;

    @BeforeEach
     void initialiseRunway() {

        runway = new Runway("09R", 4022, 280, 90, 3660, 3660, 3660, 3353, 5,100);
        runway.setStopwayLength(100);
        runway.setStopwayWidth(50);


    }

    @Test
    void getName() {
        assertTrue(runway.getName().equals("09R"),"Error with  runway getName function");
    }

    @Test
    void setName() {
        runway.setName("newName");
        assertTrue(runway.getName().equals("newName"),"Error with  runway getName function");
    }

    @Test
    void getLength() {
        assertTrue(runway.getLength()==4022,"Error with  runway getLength function");
    }

    @Test
    void setLength() {
        runway.setLength(3000);
        assertTrue(runway.getLength()==3000,"Error with  runway getLength function");
    }

    @Test
    void getWidth() {
        assertTrue(runway.getWidth()==280,"Error with  runway getWidth function");
    }

    @Test
    void setWidth() {
        runway.setWidth(300);
        assertTrue(runway.getWidth()==300,"Error with  runway getWidth function");
    }

    @Test
    void getBearing() {
        assertTrue(runway.getBearing()==90,"Error with  runway getBearing function");
    }

    @Test
    void setBearing() {
        runway.setBearing(100);
        assertTrue(runway.getBearing()==100,"Error with  runway getBearing function");
    }

    @Test
    void getTORA() {
        assertTrue(runway.getTORA()==3660,"Error with  runway getTORA function");
    }

    @Test
    void setTORA() {
        runway.setTORA(1000);
        assertTrue(runway.getTORA()==1000,"Error with  runway setTORA function");
    }

    @Test
    void getTODA() {
        assertTrue(runway.getTODA()==3660,"Error with  runway getTODA function");
    }

    @Test
    void setTODA() {
        runway.setTODA(1000);
        assertTrue(runway.getTODA()==1000,"Error with  runway setTODA function");
    }

    @Test
    void getASDA() {
        assertTrue(runway.getASDA()==3660,"Error with   runway getASDA function");
    }

    @Test
    void setASDA() {
        runway.setASDA(1000);
        assertTrue(runway.getASDA()==1000,"Error with  runway setASDA function");
    }

    @Test
    void getLDA() {
        assertTrue(runway.getLDA()==3353,"Error with  runway getLDA function");
    }

    @Test
    void setLDA() {
        runway.setLDA(1000);
        assertTrue(runway.getLDA()==1000,"Error with  runway setLDA function");
    }


    @Test
    void getDTL() {
        assertTrue(runway.getDTL()==100,"Error with  runway getDTL function");
    }

    @Test
    void setDTL() {
        runway.setDisplacedThreshold(10);
        assertTrue(runway.getDTL()==10,"Error with  runway setDTL function");
    }

    @Test
    void getClearwayLength() {
        assertTrue(runway.getClearwayLength()==5,"Error with  runway getClearwayLength function");
    }

    @Test
    void setClearwayLength() {
        runway.setClearwayLength(1);
        assertTrue(runway.getClearwayLength()==1,"Error with  runway setClearwayLength function");
    }


    @Test
    void getStopwayLength() {
        assertTrue(runway.getStopwayLength()==100,"Error with  runway getClearwayLength function");
    }

    @Test
    void setStopwayLength() {
        runway.setStopwayLength(1);
        assertTrue(runway.getStopwayLength()==1,"Error with  runway setClearwayLength function");
    }
    @Test
    void getStopwayWidth() {
        assertTrue(runway.getStopwayWidth()==50,"Error with  runway getClearwayLength function");
    }

    @Test
    void setStopwayWidth() {
        runway.setStopwayWidth(1);
        assertTrue(runway.getStopwayWidth()==1,"Error with  runway setClearwayLength function");
    }

    @Test
    void Runway(){
        boolean failed = true;
        try {
            Runway Runway = new Runway();
            failed = false;
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
        assertFalse(failed, "Error with  runway base constructor");
    }
}