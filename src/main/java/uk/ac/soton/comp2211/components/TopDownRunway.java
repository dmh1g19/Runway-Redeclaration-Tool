package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class TopDownRunway extends Canvas {

    private final double SCALEUP = 1;

    private final Runway runway;
    private final ObstacleOnRunway obstacle;
    private final double viewWidth;
    private final double viewHeight;
    private final double scaledLengthToFit;
    private final double scaledWidthToFit;

    public TopDownRunway(Runway runway, ObstacleOnRunway obstacle, double viewWidth, double viewHeight){
        this.runway = runway;
        this.obstacle = obstacle;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        //fit schematic to the viewport's width
        this.scaledLengthToFit = scaleToFitWidth(runway.getLength());
        this.scaledWidthToFit = scaleToFitWidth(runway.getWidth());

        setWidth(viewWidth);
        setHeight(viewHeight);

        representView();

        //add listener
    }

    //scale elements to fit
    public double scaleToFitWidth(double n) {
        return (viewWidth/runway.getLength())*n*SCALEUP;
    }

    public void representView() {
        var gc = getGraphicsContext2D();
        double RESA = scaleToFitWidth(240); //TODO: Pass as dynamic var
        double halfHeight = viewHeight/2;
        double rightSide = halfHeight-(scaledWidthToFit/2);

        gc.setFill(Color.color(0.02,0.024,0.024,0.4));
        gc.fillRect(0,0, viewWidth, viewHeight);

        //runway
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0,rightSide,scaledLengthToFit,scaledWidthToFit);

        //runway centre line
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.setLineDashes(5);
        gc.strokeLine(0,halfHeight,scaledLengthToFit,halfHeight);

        //Object - position may need adjusting slightly
        gc.setFill(Color.RED);
        gc.fillRect(scaleToFitWidth(obstacle.getPosition()),halfHeight-(scaleToFitWidth(obstacle.getDFCL())),
                    scaleToFitWidth(obstacle.getLength()),scaleToFitWidth(obstacle.getHeight()));

        //RESA - currently a fixed length of 240m
        gc.setFill(Color.DARKGREY);
        gc.fillRect(0,rightSide,RESA,scaledWidthToFit);
        gc.setFill(Color.DARKGREY);
        gc.fillRect(viewWidth-RESA,rightSide,RESA,scaledWidthToFit);


    }
}
