package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class TopRunway extends Canvas {

    private final Runway runway;
    private final ObstacleOnRunway obstacle;
    private final double viewWidth;
    private final double viewHeight;
    private final double scaledLengthToFit;
    private final double scaledWidthToFit;

    public TopRunway(Runway runway, ObstacleOnRunway obstacle, double viewWidth, double viewHeight){
        this.runway = runway;
        this.obstacle = obstacle;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        //fit schematic to the viewport
        this.scaledLengthToFit = (viewWidth/runway.getLength())*runway.getLength();
        this.scaledWidthToFit = (viewHeight/runway.getLength())*runway.getWidth();

        setWidth(viewWidth);
        setHeight(viewHeight);

        representView();

        //add listener
    }

   public void representView() {
        var gc = getGraphicsContext2D();

       //tmp
        gc.setFill(Color.color(0.02,0.024,0.024,0.4));
        gc.fillRect(0,0, viewWidth, viewHeight);


        gc.setFill(Color.BLACK);
        gc.fillRect(0,viewHeight/2,scaledLengthToFit,scaledWidthToFit);
   }
}
