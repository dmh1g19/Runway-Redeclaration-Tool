package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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

    //this represents view for plane taking off from left- add right towards an obstacle.
    //need to implement taking off away from an obstacle + landing
    public void representView() {
        //for now will represent plane as rectangle but implement drawImage later.
        var gc = getGraphicsContext2D();
        //gc.clearRect(0,0, height, width);
        gc.setFill(Color.color(0.02,0.024,0.024,0.4));
        gc.fillRect(0,0, width, height);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, (height - 20), width, 20); //fillRect(topLeftX, topLeftY, length, height)

        //the objects dimensions relative to the view size are calculated.
        gc.setFill(Color.RED);
        double obPos = (((double) obstacle.getPosition())/runway.getLength())*width; //relative position of object
        double obLen = (((double)obstacle.getLength())/runway.getLength())*width; //relative length of object
        double obHeight = (((double)obstacle.getHeight())/runway.getLength())*height; //relative height of object
        gc.fillRect((obPos - (obLen / 2)), (height -(obHeight + 20)), obLen, obHeight);
        //gc.fillRect(69.645, 174.29, 3.57, 5.71);


        gc.setLineWidth(3);
        gc.setStroke(Color.DARKGREEN);
        //relative sizes for TODA, 60m and the RESA are calculated.
        double TODALen = ((double) runway.getTODA() / runway.getLength()) * width;
        double sixtyLen = (60.0/runway.getLength())*width;
        double RESALen = (240.0/runway.getLength())*width;

        // length and height relative to view size are calculated.
        double ALSUp = 2 * obstacle.getHeight();
        double ALSAcross = 50 * ALSUp;
        ALSUp = (ALSUp / runway.getLength()) * height;
        ALSAcross = (ALSAcross /runway.getLength()) * height;

        //if the object is on the right side of the runway measurements taken from left
        if (obPos > width/2){
            gc.strokeLine(1,(height - 30), (TODALen-1) ,(height - 30));
            gc.fillText(("TODA:" + runway.getTODA() + "m"), (TODALen / 2) ,(height - 45));

            gc.setStroke(Color.YELLOW);
            gc.strokeLine((TODALen+1),(height - 30),(TODALen + sixtyLen - 1),(height - 30));

            gc.setStroke(Color.ORANGE);
            gc.strokeLine((TODALen + sixtyLen + 1),(height - 30),(TODALen + sixtyLen +RESALen - 1),(height - 30));
            gc.fillText(("RESA: 240m"), (TODALen + sixtyLen +(RESALen/2)) ,(height - 45));

            gc.setLineWidth(1);
            gc.setStroke(Color.BLUE);
            gc.strokeLine((width - TODALen),(height - 20),(width - TODALen) + ALSAcross,(height - 20) - ALSUp );
        }
        //if the object is on the left side of the runway measurements taken from right
        else{
            gc.strokeLine(width -1 ,(height - 30), (width - TODALen + 1) ,(height - 30));
            gc.fillText(("TODA:" + runway.getTODA() + "m"), (width - (TODALen / 2)) ,(height - 45));

            gc.setStroke(Color.YELLOW);
            gc.strokeLine((width - TODALen - 1),(height - 30),(width -(TODALen + sixtyLen - 1)),(height - 30));

            gc.setStroke(Color.ORANGE);
            gc.strokeLine((width -(TODALen + sixtyLen + 1)),(height - 30),(width -(TODALen + sixtyLen + RESALen - 1)),(height - 30));
            gc.fillText(("RESA: 240m"), (width -(TODALen + sixtyLen + (RESALen/2))) ,(height - 45));

            gc.setLineWidth(1);
            gc.setStroke(Color.BLUE);
            gc.strokeLine((width - TODALen),(height - 20),(width - TODALen) - ALSAcross,(height - 20) - ALSUp );
        }

        //don't know name for the 60m bit RETURN

        //is RESA always 240m? - RESA can be ignored for longer distances.


    }

    public void takeOffView(){

    }

    public void landingView(){

    }



}
