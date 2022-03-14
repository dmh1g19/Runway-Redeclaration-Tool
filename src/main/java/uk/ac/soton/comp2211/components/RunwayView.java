package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;

public abstract class RunwayView extends Canvas {

    private RedeclaredRunway runway;

    RunwayView (RedeclaredRunway runway, double width, double height){
        this.runway = runway;
        setWidth(width);
        setHeight(height);
    }

    public abstract void runwayUpdated(RedeclaredRunway runway);

}
