package uk.ac.soton.comp2211.airport;

public enum State {
    TAKEOFF {
        @Override
        public String toString() {
            return "Take Off";
        }
    },
    LANDING {
        @Override
        public String toString() {
            return "Landing";
        }
    }
}
