package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class SideOnRunway extends Canvas {

    /**
     * Draw the view for side-on runway here -
     */
    private final Runway runway;
    private final ObstacleOnRunway obstacle;
    private final double width;
    private final double height;

    public SideOnRunway(Runway runway, ObstacleOnRunway obstacle, double width, double height){
        this.runway = runway;
        this.obstacle = obstacle;
        this.width = width;
        this.height = height;

        setWidth(width);
        setHeight(height);

        representView();

        //add listener so that everytime runway or obstacle changed by user side on view updates.
    }

    public void representView(){
        //for now will represent plane as rectangle but implement drawImage later.
        var gc = getGraphicsContext2D();
        gc.clearRect(0,0, height, width);

        gc.fillRect(0,0, 10, width);
        double obPos = (obstacle.getPosition()/runway.getLength())*width;
        double obLen = (obstacle.getLength()/runway.getLength())*width;
        double obHeight = (obstacle.getHeight()/runway.getLength())*height;
        gc.fillRect( (obPos - (obLen/2)),10,(obPos + (obLen/2)) ,(10+obHeight) );


    }



}
