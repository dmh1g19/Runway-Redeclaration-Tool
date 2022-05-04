package uk.ac.soton.comp2211.airport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void values() {
       assertTrue(State.values().length ==2);

    }

    @Test
    void valueOf() {
        assertTrue(State.LANDING.toString().equals("Landing"),"error with converting State.Landing to String");
        assertTrue(State.TAKEOFF.toString().equals("Take Off"),"error with converting State.Landing to String");

    }
}