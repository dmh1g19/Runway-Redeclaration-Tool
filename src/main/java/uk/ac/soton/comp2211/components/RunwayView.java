package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.airport.State;

public abstract class RunwayView extends Canvas {

    private RedeclaredRunway runway;

    RunwayView (double width, double height){
        setWidth(width);
        setHeight(height);
    }

    public abstract void draw(RedeclaredRunway runway, State state);

}
