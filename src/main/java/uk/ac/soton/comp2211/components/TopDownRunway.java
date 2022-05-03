package uk.ac.soton.comp2211.components;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import uk.ac.soton.comp2211.airport.*;

import static java.lang.Math.round;

public class TopDownRunway extends RunwayView {

    //scaled down runway dimension
    double runwayLen = 650;
    double runwayWidth = 50;



    GraphicsContext gc = this.getGraphicsContext2D();

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

        //clear canvas before drawing -- for when you have to redraw
        gc.clearRect(0,0, getWidth(), getHeight());

        //this gc.save() is essential for rotating and scaling, dont remove x
        gc.save();
        transformRunway(bearing-90,0.5 /*temporary scale to make the runway scale within window*/);

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

        System.out.println(runway.getName());
        int bearing = ((int) Math.round(runway.getBearing()))/10 ;
        String closeLetter = "";
        String farLetter = "";
        if(runway.getName().contains("L")){
            closeLetter="L";
            farLetter="R";

        }else if(runway.getName().contains("R")){
            closeLetter="R";
            farLetter="L";
        }

        if(bearing < 18){
            thresholdR(String.valueOf(bearing+18), farLetter);
            if(bearing <10){
                thresholdL("0"+ bearing,closeLetter);
            }else{
                thresholdL(String.valueOf(bearing),closeLetter);
            }
        }else{
            thresholdL(String.valueOf(bearing),closeLetter);
            if(bearing <28){
                thresholdR("0"+ (bearing - 18),farLetter);;
            }else{
                thresholdR(String.valueOf(bearing-18),farLetter);
            }

        }

        //LEFT THR MARKING
        //thresholdL(String.valueOf(round(bearing)),"L");


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

        gc.restore();
        legend();
        compass();
    }

    public void landingAndTakeOffAwayFromObj_LowestThreshold() {



        //TODA
        gc.setStroke(TODA_COLOR);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen,50,scaledTODA);
        //TORA
        gc.setStroke(TORA_COLOR);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen,70,scaledTORA);
        //LDA
        gc.setStroke(LDA_COLOR);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen+scaledDTL,90,scaledLDA);
        //ASDA
        gc.setStroke(ASDA_COLOR);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen,110,scaledASDA);
        //RESA
        gc.setStroke(RESA_COLOR);
        lineMarking(startOfRunwayX+scaledObjPos+scaledObjLen+scaledDTL, 130,scaledRESA);
    }

    public void landingAndTakeOffTowardsObj_LowestThreshold() {



        //TODA
        gc.setStroke(TODA_COLOR);
        lineMarking(startOfRunwayX, 90, scaledTODA);
        //TORA
        gc.setStroke(TORA_COLOR);
        lineMarking(startOfRunwayX, 70, scaledTORA);
        //LDA
        gc.setStroke(LDA_COLOR);
        lineMarking(startOfRunwayX+scaledDTL, 50, scaledLDA);
        //ASDA
        gc.setStroke(ASDA_COLOR);
        lineMarking(startOfRunwayX, 110, scaledASDA);
        //RESA
        gc.setStroke(RESA_COLOR);
        if (scaledObjPos > 0) {
            lineMarking((startOfRunwayX-scaledRESA)+scaledObjPos+scaledDTL, 50, scaledRESA);
        }
        else {
            lineMarking(startOfRunwayX+scaledDTL, 50, scaledRESA);
        }
    }

    public void landingAndTakeOffTowardsObj_HighestThreshold() {



        //TODA
        gc.setStroke(TODA_COLOR);
        lineMarking(endOfRunwayX-scaledTODA, 90, scaledTODA);
        //TORA
        gc.setStroke(TORA_COLOR);
        lineMarking(endOfRunwayX-scaledTODA, 70, scaledTORA);
        //LDA
        gc.setStroke(LDA_COLOR);
        lineMarking(endOfRunwayX-scaledLDA+scaledDTL, 50, scaledLDA);
        //ASDA
        gc.setStroke(ASDA_COLOR);
        lineMarking(endOfRunwayX-scaledASDA, 110, scaledASDA);
        //RESA
        gc.setStroke(RESA_COLOR);
        lineMarking(endOfRunwayX-scaledRESA, 50, scaledRESA);
    }

    public void landingAndTakeOffAwayFromObj_HighestThreshold() {



        //TODA
        gc.setStroke(TODA_COLOR);
        lineMarking(startOfRunwayX-scaledTODA+scaledObjPos, 90, scaledTODA);
        //TORA
        gc.setStroke(TORA_COLOR);
        lineMarking(startOfRunwayX-scaledTORA+scaledObjPos, 70, scaledTORA);
        //LDA
        gc.setStroke(LDA_COLOR);
        lineMarking(startOfRunwayX-scaledLDA+scaledObjPos+scaledDTL, 50, scaledLDA);
        //ASDA
        gc.setStroke(ASDA_COLOR);
        lineMarking(startOfRunwayX-scaledASDA+scaledObjPos, 110, scaledASDA);
        //RESA
        gc.setStroke(RESA_COLOR);
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
       gc.fillText(pos, endOfRunwayX-scaledRESA-35,halfHeight-25);
        gc.setFont(new Font(25));
        gc.fillText(thr, endOfRunwayX-scaledRESA-40,halfHeight-50);
        gc.restore();
    }

    public void legend() {

        //TODA
        gc.setFill(TODA_COLOR);
        gc.fillRoundRect(startOfRunwayX,300, 10,10,5,5);
        gc.fillText(": TODA " + runway.getTODA() + "m",startOfRunwayX+15,310);

        //TORA
        gc.setFill(TORA_COLOR);
        gc.fillRoundRect(startOfRunwayX,320, 10,10,5,5);
        gc.fillText(": TORA " + runway.getTORA() + "m",startOfRunwayX+15,330);

        //LDA
        gc.setFill(LDA_COLOR);
        gc.fillRoundRect(startOfRunwayX,340, 10,10,5,5);
        gc.fillText(": LDA " + runway.getLDA() + "m",startOfRunwayX+15,350);

        //RESA
        gc.setFill(RESA_COLOR);
        gc.fillRoundRect(startOfRunwayX,360, 10,10,5,5);
        gc.fillText(": RESA " + 240 + "m",startOfRunwayX+15,370);

        //ASDA
        gc.setFill(ASDA_COLOR);
        gc.fillRoundRect(startOfRunwayX,380, 10,10,5,5);
        gc.fillText(": ASDA " + runway.getASDA() + "m",startOfRunwayX+15,390);

    }

    public void compass()
    {
        double width = getWidth();
        double height = getHeight();
        //A compass in the top right of the screen that shows the bearing of the runway
        gc.setFill(Color.WHITE);
        gc.fillOval(width - 100, height/10 , 50,50);
        gc.strokeOval(width - 100, height/10 , 50,50);
        gc.setStroke(Color.BLUE);
        gc.save();
        gc.transform(new Affine(new Rotate(bearing,(width - 75),(height/10 +25))));
        gc.strokeLine(width - 75, height/10 +25, width - 75, height/10 + 7  );
        gc.restore();
        gc.setStroke(Color.RED);
        gc.strokeLine(width - 75, height/10 +25, width - 75, height/10 + 7  );
        gc.setFill(Color.RED);
        gc.fillText("N" ,width - 79,height/10 -5 );
        gc.setFill(Color.WHITE);
        gc.fillText("Bearing" ,width - 45,height/10 +30 );
        gc.fillText(bearing+"",width - 45,height/10 + 20 );
    }

    public void transformRunway(double rotDegrees, double scale)
    {
        gc.transform(new Affine(new Scale(scale,scale,this.halfWidth,halfHeight)));
        gc.transform(new Affine(new Rotate(rotDegrees,this.halfWidth,halfHeight)));
    }

}