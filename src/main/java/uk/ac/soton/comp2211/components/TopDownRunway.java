package uk.ac.soton.comp2211.components;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import org.junit.platform.engine.support.hierarchical.OpenTest4JAwareThrowableCollector;
import uk.ac.soton.comp2211.airport.*;

public class TopDownRunway extends RunwayView {

    //scaled down runway dimension
    double runwayLen = 650;
    double runwayWidth = 50;

    private final Canvas runwayCanvas = new Canvas();
    private final Canvas UICanvas = new Canvas();

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

    public TopDownRunway(RedeclaredRunway runway, double width, double height) {
        super(runway,width, height);

        this.runway = runway.getRunway();
        this.obstacle = runway.getObstacle();

        runwayCanvas.setWidth(700); runwayCanvas.setHeight(400);
        UICanvas.setWidth(700); UICanvas.setHeight(400);

        //runwayCanvas.getGraphicsContext2D().rotate(this.runway.getBearing()-90);
        draw(runway);

    }

    public void draw(RedeclaredRunway runway1) {
        this.runway = runway1.getRunway();
        this.obstacle = runway1.getObstacle();

        runway.setDTL(0);

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
        this.scaledObjWidth = scaleToRunwayWidth(obstacle.getLength());


        GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
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
        gc.fillRect(startOfRunwayX+scaledObjPos, (halfHeight-(scaledObjLen/2))+scaledObjDFCL,scaledObjWidth,scaledObjLen);

        landingAndTakeOffTowardsObj_LowestThreshold();
        //landingAndTakeOffAwayFromObj_LowestThreshold();

        legend();
    }

    public void legend() {
        GraphicsContext gc = UICanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRoundRect(startOfRunwayX,300, 10,10,5,5);
        gc.fillText(": TODA " + runway.getTODA(),startOfRunwayX+15,310);
        gc.setFill(Color.BLUE);
        gc.fillRoundRect(startOfRunwayX,320, 10,10,5,5);
        gc.fillText(": TORA " + runway.getTORA(),startOfRunwayX+15,330);
        gc.setFill(Color.YELLOW);
        gc.fillRoundRect(startOfRunwayX,340, 10,10,5,5);
        gc.fillText(": LDA " + runway.getLDA(),startOfRunwayX+15,350);
        gc.setFill(Color.GREEN);
        gc.fillRoundRect(startOfRunwayX,360, 10,10,5,5);
        gc.fillText(": RESA " + runway.getClearwayLength(),startOfRunwayX+15,370);
        gc.setFill(Color.ORANGE);
        gc.fillRoundRect(startOfRunwayX,380, 10,10,5,5);
        gc.fillText(": ASDA " + runway.getASDA(),startOfRunwayX+15,390);
    }

    public void landingAndTakeOffTowardsObj_LowestThreshold() {
        GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        gc.setLineDashes(0);
        //TODA
        gc.setStroke(Color.RED);
        lineMarking(startOfRunwayX, 50, startOfRunwayX, scaledTODA);
        //TORA
        gc.setStroke(Color.BLUE);
        lineMarking(startOfRunwayX, 70, startOfRunwayX, scaledTORA);
        //LDA
        gc.setStroke(Color.YELLOW);
        lineMarking(startOfRunwayX, 90, startOfRunwayX, scaledLDA);
        //RESA
        gc.setStroke(Color.GREEN);
        lineMarking(startOfRunwayX, 90, startOfRunwayX, scaledRESA);
        //ASDA
        gc.setStroke(Color.ORANGE);
        lineMarking(startOfRunwayX, 110, startOfRunwayX, scaledASDA);
    }

    public double scaleToRunwayLength (double object) {
        return (runwayLen/runway.getLength())*object;
    }

    public double scaleToRunwayWidth (double object) {
        return (runwayWidth/runway.getWidth())*object;
    }

    public void lineMarking(double start, double spacing, double end, double distance) {
        if (distance < 0) {
            distance = 0;
        }
        else {
            runwayCanvas.getGraphicsContext2D().strokeLine(start,halfHeight-spacing,end+distance,halfHeight-spacing);
        }
    }

    public void thresholdL(String thr, String pos) {
        GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
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
        GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
        gc.save();
        gc.setFill(Color.WHITE);
        gc.transform(new Affine(new Rotate(270,endOfRunwayX-scaledRESA-15,halfHeight-(runwayWidth/2)+15)));
        gc.setFont(new Font(30));
        gc.fillText("R", endOfRunwayX-scaledRESA-35,halfHeight-25);
        gc.setFont(new Font(25));
        gc.fillText("27", endOfRunwayX-scaledRESA-40,halfHeight-50);
        gc.restore();
    }

    public Canvas getRunwayCanvas() { return runwayCanvas; }
    public Canvas getUICanvas() { return UICanvas; }

    public void runwayUpdated(RedeclaredRunway runway){
        draw(runway);
    }
}