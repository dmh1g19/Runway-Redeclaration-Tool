package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class TopDownRunway extends Canvas {

    //scaled down runway dimension
    double runwayLen = 650;
    double runwayWidth = 50;

    private final Runway runway;
    private final ObstacleOnRunway obstacle;
    private final double viewWidth;
    private final double viewHeight;
    private final String direction;

    public TopDownRunway(Runway runway, ObstacleOnRunway obstacle, double viewWidth, double viewHeight, String direction) {
        this.runway = runway;
        this.obstacle = obstacle;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.direction = direction;

        setWidth(viewWidth);
        setHeight(viewHeight);

        representView();
    }

    public double scaleToRunwayLength (double object) {
        return (runwayLen/runway.getLength())*object;
    }

    public double scaleToRunwayWidth (double object) {
        return (runwayWidth/runway.getWidth())*object;
    }

    public void representView() {

        double halfHeight = viewHeight/2;
        double halfWidth = viewWidth/2;
        double startOfRunwayX= halfWidth-(runwayLen/2);
        double startOfRunwayY= halfHeight-(runwayWidth/2);
        double endOfRunwayX = halfWidth+(runwayLen/2);

        double scaledRESA = scaleToRunwayLength(240);
        double scaledLDA = scaleToRunwayLength(runway.getLDA());
        double scaledTORA = scaleToRunwayLength(runway.getTORA());
        double scaledTODA = scaleToRunwayLength(runway.getTODA());
        double scaledObjPos = scaleToRunwayLength(obstacle.getPosition());
        double scaledObjDFCL = scaleToRunwayWidth(obstacle.getDFCL());
        double scaledObjLen = scaleToRunwayLength(obstacle.getLength());
        double scaledObjLen2 = scaleToRunwayWidth(obstacle.getLength());

        var gc = getGraphicsContext2D();

        //viewport
        gc.setFill(Color.color(0.06,0.25,0.06));
        gc.fillRect(0,0, viewWidth, viewHeight);

        Path gradedArea = new Path();

        MoveTo start = new MoveTo();
        start.setX(0);
        start.setY(0);



        //grass
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(startOfRunwayX-5, startOfRunwayY-5, runwayLen+10, runwayWidth+10);

        //runway
        gc.setFill(Color.BLACK);
        gc.fillRect(startOfRunwayX, startOfRunwayY, runwayLen, runwayWidth);

        //runway centre line
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.setLineDashes(10);
        gc.strokeLine(startOfRunwayX,halfHeight,startOfRunwayX+runwayLen,halfHeight);

        //LEFT THR MARKING
        gc.save();
        gc.setFill(Color.WHITE);
        gc.transform(new Affine(new Rotate(90,startOfRunwayX+scaledRESA+15,halfHeight-(runwayWidth/2)+15)));
        gc.setFont(new Font(30));
        gc.fillText("L", startOfRunwayX+scaledRESA+20,halfHeight-25);
        gc.setFont(new Font(25));
        gc.fillText("09", startOfRunwayX+scaledRESA+10,halfHeight-50);
        gc.restore();

        //RIGHT THR MARKING
        gc.save();
        gc.setFill(Color.WHITE);
        gc.transform(new Affine(new Rotate(270,endOfRunwayX-(scaledRESA)-15,halfHeight-(runwayWidth/2)+15)));
        gc.setFont(new Font(30));
        gc.fillText("R", endOfRunwayX-scaledRESA-35,halfHeight-25);
        gc.setFont(new Font(25));
        gc.fillText("27", endOfRunwayX-scaledRESA-40,halfHeight-50);
        gc.restore();

        //RESA1
        gc.setFill(Color.WHITE);
        gc.fillRect(startOfRunwayX, startOfRunwayY, scaledRESA, runwayWidth);
        //RESA2
        gc.setFill(Color.WHITE);
        gc.fillRect(endOfRunwayX-scaledRESA, startOfRunwayY, scaledRESA, runwayWidth);

        //object
        gc.setFill(Color.RED);
        gc.fillRect(startOfRunwayX+scaledObjPos, (halfHeight-(scaledObjLen/2))+scaledObjDFCL,scaledObjLen2,scaledObjLen);
        //gc.fillOval(startOfRunwayX+scaledObjPos, (halfHeight-(scaledObjLen/2))+scaledObjDFCL,scaledObjLen2,scaledObjLen);

        //TODA
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.setLineDashes(0);
        gc.strokeLine(startOfRunwayX,halfHeight-50,startOfRunwayX+scaledTODA,halfHeight-50);
        //TORA
        gc.setStroke(Color.BLACK);
        gc.strokeLine(startOfRunwayX,halfHeight-70,startOfRunwayX+scaledTORA,halfHeight-70);
        //LDA
        gc.setStroke(Color.BLACK);
        gc.strokeLine(startOfRunwayX,halfHeight-90,startOfRunwayX+scaledLDA,halfHeight-90);
        //RESA
        gc.setStroke(Color.BLACK);
        gc.strokeLine(startOfRunwayX,halfHeight-110,startOfRunwayX+scaledRESA,halfHeight-110);
    }

    //public void takeOff() {
    ////Away from the object

    ////Towards the object

    //}
}
