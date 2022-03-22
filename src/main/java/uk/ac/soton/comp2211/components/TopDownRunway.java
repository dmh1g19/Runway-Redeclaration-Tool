package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import uk.ac.soton.comp2211.airport.*;

import static java.lang.Math.round;

public class TopDownRunway extends RunwayView {

    //scaled down runway dimension
    double runwayLen = 650;
    double runwayWidth = 50;

    GraphicsContext gc = getGraphicsContext2D();

    private Runway runway;
    private ObstacleOnRunway obstacle;

    double halfHeight;
    double halfWidth;
    double startOfRunwayX;
    double startOfRunwayY;
    double endOfRunwayX;

    double scaledASDA;
    double scaledDTL;
    double scaledRESA;
    double scaledLDA;
    double scaledTORA;
    double scaledTODA;
    double scaledObjPos;
    double scaledObjLen;
    double scaledObjDFCL;
    double scaledObjWidth;

    double bearing;

    Direction direction;

    public TopDownRunway( double width, double height)  {
        super(width, height);

        this.runway = new Runway("09L",3000,0,0,0,0,0,0,0,0);
        this.obstacle = new ObstacleOnRunway("test", 0, 0, 0, 0,0);

        //tmp screen with nothing on it
        draw(new RedeclaredRunway(runway,obstacle,Direction.TOWARDS), State.TAKEOFF);
    }

    @Override
    public void draw(RedeclaredRunway runway1, State state) {

        this.runway = runway1.getRunway();
        this.obstacle = runway1.getObstacle();
        this.direction = runway1.getDirection();

        //runway.setDTL(300);

        this.bearing = runway.getBearing();
        this.halfHeight = getHeight()/2;
        this.halfWidth = getWidth()/2;
        this.startOfRunwayX = halfWidth-(runwayLen/2);
        this.startOfRunwayY = halfHeight-(runwayWidth/2);
        this.endOfRunwayX = halfWidth+(runwayLen/2);

        this.scaledASDA = scaleToRunwayLength(runway.getASDA());
        this.scaledDTL = scaleToRunwayLength(runway.getDTL());
        this.scaledRESA = scaleToRunwayLength(240);
        this.scaledLDA = scaleToRunwayLength(runway.getLDA());
        this.scaledTORA = scaleToRunwayLength(runway.getTORA());
        this.scaledTODA = scaleToRunwayLength(runway.getTODA());
        this.scaledObjPos = scaleToRunwayLength(obstacle.getPosition());
        this.scaledObjLen = scaleToRunwayLength(obstacle.getLength());
        this.scaledObjDFCL = scaleToRunwayWidth(obstacle.getDFCL());
        this.scaledObjWidth = scaleToRunwayWidth(obstacle.getWidth());

        //background
        gc.setFill(Color.color(0.1,0.1,0.1));
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
        //thresholdL(String.valueOf(round(bearing)),"L");
        thresholdL("09","L");
        //RIGHT THR MARKING
        thresholdR("27","R");

        gc.setFill(Color.WHITE);
        //RESA1
        gc.fillRect(startOfRunwayX+scaledDTL, startOfRunwayY, scaledRESA, runwayWidth);
        //RESA2
        gc.fillRect(endOfRunwayX-scaledRESA, startOfRunwayY, scaledRESA, runwayWidth);

        //disp thres
        gc.setFill(Color.YELLOW);
        gc.fillRect(startOfRunwayX, startOfRunwayY, scaledDTL, runwayWidth);

        //Object
        gc.setFill(Color.RED);
        gc.fillRect(startOfRunwayX+scaledObjPos, (halfHeight-(scaledObjWidth/2))+scaledObjDFCL,scaledObjLen,scaledObjWidth);

        //Draw distances according to what the plane is doing
        gc.setLineWidth(2);
        gc.setLineDashes(0);
        if (direction == Direction.TOWARDS) {
            landingAndTakeOffTowardsObj_LowestThreshold();
        }
        else if (direction == Direction.AWAYOVER) {
            landingAndTakeOffAwayFromObj_LowestThreshold();
        }
        //else if (for when direction TOWARDS for highest threshold) {
        //    landingAndTakeOffTowardsObj_HighestThreshold();
        //}
        //else if (for when direction AWAY for highest threshold) {
        //    landingAndTakeOffAwayFromObj_HighestThreshold();
        //}


    legend();
    }

    public void landingAndTakeOffAwayFromObj_LowestThreshold() {
        //TODA
        gc.setStroke(Color.RED);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen,50,scaledTODA);
        //TORA
        gc.setStroke(Color.BLUE);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen,70,scaledTORA);
        //LDA
        gc.setStroke(Color.YELLOW);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen+scaledDTL,90,scaledLDA);
        //ASDA
        gc.setStroke(Color.ORANGE);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen,110,scaledASDA);
        //RESA
        gc.setStroke(Color.GREEN);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen+scaledDTL, 130,scaledRESA);
    }

    public void landingAndTakeOffTowardsObj_LowestThreshold() {
        //TODA
        gc.setStroke(Color.RED);
        lineMarking(startOfRunwayX, 90, scaledTODA);
        //TORA
        gc.setStroke(Color.BLUE);
        lineMarking(startOfRunwayX, 70, scaledTORA);
        //LDA
        gc.setStroke(Color.YELLOW);
        lineMarking(startOfRunwayX+scaledDTL, 50, scaledLDA);
        //ASDA
        gc.setStroke(Color.ORANGE);
        lineMarking(startOfRunwayX, 110, scaledASDA);
        //RESA
        gc.setStroke(Color.WHITE);
        if (scaledObjPos > 0) {
            lineMarking((startOfRunwayX-scaledRESA)+scaledObjPos+scaledDTL, 50, scaledRESA);
        }
        else {
            lineMarking(startOfRunwayX+scaledDTL, 50, scaledRESA);
        }
    }

    public void landingAndTakeOffTowardsObj_HighestThreshold() {
        //TODA
        gc.setStroke(Color.RED);
        lineMarking(endOfRunwayX-scaledTODA, 90, scaledTODA);
        //TORA
        gc.setStroke(Color.BLUE);
        lineMarking(endOfRunwayX-scaledTODA, 70, scaledTORA);
        //LDA
        gc.setStroke(Color.YELLOW);
        lineMarking(endOfRunwayX-scaledLDA+scaledDTL, 50, scaledLDA);
        //ASDA
        gc.setStroke(Color.ORANGE);
        lineMarking(endOfRunwayX-scaledASDA, 110, scaledASDA);
        //RESA
        gc.setStroke(Color.WHITE);
        lineMarking(endOfRunwayX-scaledRESA, 50, scaledRESA);
    }

    public void landingAndTakeOffAwayFromObj_HighestThreshold() {
        //TODA
        gc.setStroke(Color.RED);
        lineMarking(startOfRunwayX-scaledTODA+scaledObjPos, 90, scaledTODA);
        //TORA
        gc.setStroke(Color.BLUE);
        lineMarking(startOfRunwayX-scaledTORA+scaledObjPos, 70, scaledTORA);
        //LDA
        gc.setStroke(Color.YELLOW);
        lineMarking(startOfRunwayX-scaledLDA+scaledObjPos+scaledDTL, 50, scaledLDA);
        //ASDA
        gc.setStroke(Color.ORANGE);
        lineMarking(startOfRunwayX-scaledASDA+scaledObjPos, 110, scaledASDA);
        //RESA
        gc.setStroke(Color.WHITE);
        lineMarking(startOfRunwayX-scaledRESA+scaledObjPos, 50, scaledRESA);
    }

    public double scaleToRunwayLength (double object) {
        return (runwayLen/runway.getLength())*object;
    }

    public double scaleToRunwayWidth (double object) {
        return (runwayWidth/runway.getWidth())*object;
    }

    public void lineMarking(double start, double spacing, double distance) {
        //set all markings to 0 if there is no object on the runway
        if (distance <= 0) {
            distance = 0;
        }
        else {
            gc.strokeLine(start,halfHeight-spacing,start+distance,halfHeight-spacing);
        }
    }

    public void thresholdL(String thr, String pos) {
        gc.save();
        gc.setFill(Color.WHITE);
        gc.transform(new Affine(new Rotate(90,startOfRunwayX+scaledRESA+scaledDTL+15,halfHeight-(runwayWidth/2)+15)));
        gc.setFont(new Font(30));
        gc.fillText(pos, startOfRunwayX+scaledRESA+scaledDTL+20,halfHeight-25);
        gc.setFont(new Font(25));
        gc.fillText(thr, startOfRunwayX+scaledRESA+scaledDTL+10,halfHeight-50);
        gc.restore();
    }

    public void thresholdR(String thr, String pos) {
        gc.save();
        gc.setFill(Color.WHITE);
        gc.transform(new Affine(new Rotate(270,endOfRunwayX-scaledRESA-15,halfHeight-(runwayWidth/2)+15)));
        gc.setFont(new Font(30));
        gc.fillText("R", endOfRunwayX-scaledRESA-35,halfHeight-25);
        gc.setFont(new Font(25));
        gc.fillText("27", endOfRunwayX-scaledRESA-40,halfHeight-50);
        gc.restore();
    }

    public void legend() {
        gc.setFill(Color.RED);
        gc.fillRoundRect(startOfRunwayX,300, 10,10,5,5);
        gc.fillText(": TODA " + runway.getTODA() + "m",startOfRunwayX+15,310);
        gc.setFill(Color.BLUE);
        gc.fillRoundRect(startOfRunwayX,320, 10,10,5,5);
        gc.fillText(": TORA " + runway.getTORA() + "m",startOfRunwayX+15,330);
        gc.setFill(Color.YELLOW);
        gc.fillRoundRect(startOfRunwayX,340, 10,10,5,5);
        gc.fillText(": LDA " + runway.getLDA() + "m",startOfRunwayX+15,350);
        gc.setFill(Color.WHITE);
        gc.fillRoundRect(startOfRunwayX,360, 10,10,5,5);
        gc.fillText(": RESA " + 240 + "m",startOfRunwayX+15,370);
        gc.setFill(Color.ORANGE);
        gc.fillRoundRect(startOfRunwayX,380, 10,10,5,5);
        gc.fillText(": ASDA " + runway.getASDA() + "m",startOfRunwayX+15,390);
    }
}