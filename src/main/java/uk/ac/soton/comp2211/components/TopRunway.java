package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class TopRunway extends Canvas {

    private final double SCALEUP = 10;

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

        //fit schematic to the viewport's width
        this.scaledLengthToFit = scaleToFitWidth(runway.getLength());
        this.scaledWidthToFit = scaleToFitWidth(runway.getWidth())*SCALEUP;

        setWidth(viewWidth);
        setHeight(viewHeight);

        representView();

        //add listener
    }

    //scale elements to fit
    public double scaleToFitWidth(double n) {
        return (viewWidth/runway.getLength())*n;
    }

   public void representView() {
        var gc = getGraphicsContext2D();
        double RESA = scaleToFitWidth(240); //TODO: Pass as dynamic var
        double rightSide = (viewHeight/2)-(scaledWidthToFit/2);

        gc.setFill(Color.color(0.02,0.024,0.024,0.4));
        gc.fillRect(0,0, viewWidth, viewHeight);

        //runway
        gc.setFill(Color.color(0.02,0.024,0.024,0.4));
        gc.fillRect(0,rightSide,scaledLengthToFit,scaledWidthToFit);

       //runway lines
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.setLineDashes(5);
        gc.strokeLine(0,viewHeight/2,scaledLengthToFit,viewHeight/2);

        //TODO: object - put into correct position (currently only a square)
        gc.setFill(Color.RED);
        gc.fillRect(0,0,scaleToFitWidth(obstacle.getLength())*SCALEUP,scaleToFitWidth(obstacle.getHeight())*SCALEUP);

       //RESA - currently a fixed length of 240m
       gc.setFill(Color.DARKGREY);
       gc.fillRect(0,rightSide,RESA,scaledWidthToFit);
       gc.setFill(Color.DARKGREY);
       gc.fillRect(viewWidth-RESA,rightSide,RESA,scaledWidthToFit);
   }
}
