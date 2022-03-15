package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class TopDownRunway extends Canvas {

    //scaled down runway dimension
    double runwayLen = 650;
    double runwayWidth = 50;

    private Runway runway;
    private ObstacleOnRunway obstacle;

    public TopDownRunway(RedeclaredRunway runway) {
        this.runway = runway.getRunway();
        this.obstacle = runway.getObstacle();

        setWidth(700);
        setHeight(400);

        draw(runway);
    }

    public double scaleToRunwayLength (double object) {
        return (runwayLen/runway.getLength())*object;
    }

    public double scaleToRunwayWidth (double object) {
        return (runwayWidth/runway.getWidth())*object;
    }

    public void draw(RedeclaredRunway runway1) {
        this.runway = runway1.getRunway();
        this.obstacle = runway1.getObstacle();


        double halfHeight = getHeight()/2;
        double halfWidth = getWidth()/2;
        double startOfRunwayX = halfWidth-(runwayLen/2);
        double startOfRunwayY = halfHeight-(runwayWidth/2);
        double endOfRunwayX = halfWidth+(runwayLen/2);

        double scaledDTL = scaleToRunwayLength(runway.getDTL());
        double scaledRESA = scaleToRunwayLength(240);
        double scaledLDA = scaleToRunwayLength(runway.getLDA());
        double scaledTORA = scaleToRunwayLength(runway.getTORA());
        double scaledTODA = scaleToRunwayLength(runway.getTODA());
        double scaledObjPos = scaleToRunwayLength(obstacle.getPosition());
        double scaledObjDFCL = scaleToRunwayWidth(obstacle.getDFCL());
        double scaledObjLen = scaleToRunwayLength(obstacle.getLength());
        double scaledObjWidth = scaleToRunwayWidth(obstacle.getLength());

        var gc = getGraphicsContext2D();

        //viewport
        gc.setFill(Color.GREY);
        gc.fillRect(0,0, getWidth(), getHeight());

        //graded area
        gc.setFill(Color.GREEN);
        double[] xPoints = new double[] {0,getWidth()/5,getWidth()/5,getWidth()-(getWidth()/5),getWidth()-(getWidth()/5),getWidth(),
                getWidth(),getWidth()-(getWidth()/5),getWidth()-(getWidth()/5),getWidth()/5,getWidth()/5,0};
        double[] yPoints = new double[] {getHeight()/3,getHeight()/3,getHeight()/4,getHeight()/4,getHeight()/3,getHeight()/3,
                getHeight()-(getHeight()/3),getHeight()-(getHeight()/3),getHeight()-(getHeight()/4),getHeight()-(getHeight()/4),getHeight()-(getHeight()/3),getHeight()-(getHeight()/3)};
        gc.fillPolygon(xPoints,yPoints,12);

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
        gc.transform(new Affine(new Rotate(90,startOfRunwayX+scaledRESA+scaledDTL+15,halfHeight-(runwayWidth/2)+15)));
        gc.setFont(new Font(30));
        gc.fillText("L", startOfRunwayX+scaledRESA+scaledDTL+20,halfHeight-25);
        gc.setFont(new Font(25));
        gc.fillText("09", startOfRunwayX+scaledRESA+scaledDTL+10,halfHeight-50);
        gc.restore();

        //RIGHT THR MARKING
        gc.save();
        gc.setFill(Color.WHITE);
        gc.transform(new Affine(new Rotate(270,endOfRunwayX-scaledRESA-15,halfHeight-(runwayWidth/2)+15)));
        gc.setFont(new Font(30));
        gc.fillText("R", endOfRunwayX-scaledRESA-35,halfHeight-25);
        gc.setFont(new Font(25));
        gc.fillText("27", endOfRunwayX-scaledRESA-40,halfHeight-50);
        gc.restore();

        //RESA1
        gc.setFill(Color.WHITE);
        gc.fillRect(startOfRunwayX+scaledDTL, startOfRunwayY, scaledRESA, runwayWidth);
        //RESA2
        gc.setFill(Color.WHITE);
        gc.fillRect(endOfRunwayX-scaledRESA, startOfRunwayY, scaledRESA, runwayWidth);

        //disp thres
        gc.setFill(Color.YELLOW);
        gc.fillRect(startOfRunwayX, startOfRunwayY, scaledDTL, runwayWidth);

        //Object
        gc.setFill(Color.RED);
        gc.fillRect(startOfRunwayX+scaledObjPos, (halfHeight-(scaledObjLen/2))+scaledObjDFCL,scaledObjWidth,scaledObjLen);

        //TODA
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.setLineDashes(0);
        gc.strokeLine(startOfRunwayX,halfHeight-50,startOfRunwayX+scaledTODA,halfHeight-50);
        //TORA
        gc.setStroke(Color.BLUE);
        gc.strokeLine(startOfRunwayX,halfHeight-70,startOfRunwayX+scaledTORA,halfHeight-70);
        //LDA
        gc.setStroke(Color.YELLOW);
        gc.strokeLine(startOfRunwayX,halfHeight-90,startOfRunwayX+scaledLDA,halfHeight-90);
        //RESA
        gc.setStroke(Color.GREEN);
        gc.strokeLine(startOfRunwayX,halfHeight-110,startOfRunwayX+scaledRESA,halfHeight-110);
    }

    public void runwayUpdated(RedeclaredRunway runway){
        draw(runway);
    }
}
