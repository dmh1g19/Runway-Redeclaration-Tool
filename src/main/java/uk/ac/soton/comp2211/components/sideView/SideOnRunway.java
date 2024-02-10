package uk.ac.soton.comp2211.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import uk.ac.soton.comp2211.airport.*;

public class SideOnRunway extends RunwayView {

    private static final double RESA_LENGTH = 240.0;
    private static final double SLOPE_CALCULATION_BASE = 50.0;
    private static final Color LANDING_STRIP_COLOR = Color.BLACK;
    private static final Color CLEARWAY_COLOR = Color.LIME;
    private static final Color STOPWAY_COLOR = Color.DARKGRAY;
    private static final Color DTL_COLOR = Color.YELLOW;
    private static final Color BACKGROUND_COLOR = Color.LIGHTBLUE;
    private static final double LANDING_STRIP_HEIGHT = 20;

    private RedeclaredRunway runway;
    private RedeclaredRunway runway2;
    private ObstacleOnRunway obstacle;
    
    private double width;
    private double height;
    private double obPos;
    private double obLen;
    private double obHeight;
    private double TODALen;
    private double TORALen;
    private double ASDALen;
    private double LDALen;
    private double sixtyLen;
    private double RESALen;
    private double scaledDTL;
    private double scaledBlast;
    private double scaledRESA;
    private double closeClearwayLength;
    private double farClearwayLength;
    private double closeStopwayLength;
    private double farStopwayLength;
    private double ALSUp;
    private double ALSAcross;
    private double overallLength;
    private double currentEnd;

    private Direction dToObstacle;
    private Character rwayDirection;
    private boolean reflected = false;
    private State state;
    private GraphicsContext gc;

    public SideOnRunway(double width, double height) {
        super(width, height);
        this.gc = getGraphicsContext2D();
        this.width = getWidth();
        this.height = getHeight();
        this.currentEnd = 0;
    }

    @Override
    public void redraw() {
        draw(runway, runway2, state);
    }

    private void initializeRunwayConfiguration(RedeclaredRunway runway1, RedeclaredRunway runway2, State state) {
        this.runway = runway1;
        this.runway2 = runway2;
        this.state = state;
        this.obstacle = runway.getObstacle();
        this.dToObstacle = runway.getDirection();
        this.rwayDirection = runway.getRunway().getName().charAt(2);
        this.reflected = runway.getRunway().getBearing() > 181;
        setReflection();
    }

    private void setReflection() {
        this.setScaleX(reflected ? -1 : 1);
    }

    private void calculateDimensions() {
        calculateOverallLength();
        calculateObstacleDimensions();
        calculateRunwayDimensions();
        calculateStopwayAndClearwayLengths();
    }

    private void calculateOverallLength() {
        overallLength = runway.getRunway().getLength() + runway.getRunway().getStopwayLength() +
                runway.getRunway().getClearwayLength() + runway2.getRunway().getStopwayLength() +
                runway2.getRunway().getClearwayLength();
    }

    private void calculateObstacleDimensions() {
        obPos = (obstacle.getPosition() / overallLength) * width;
        obLen = (obstacle.getLength() / overallLength) * width;
        obHeight = (obstacle.getHeight() / overallLength) * height;
    }

    private void calculateRunwayDimensions() {
        TODALen = (runway.getRunway().getTODA() / overallLength) * width;
        TORALen = (runway.getRunway().getTORA() / overallLength) * width;
        ASDALen = (runway.getRunway().getASDA() / overallLength) * width;
        LDALen = (runway.getRunway().getLDA() / overallLength) * width;
        sixtyLen = (60.0 / overallLength) * width;
        RESALen = (240.0 / overallLength) * width;
        scaledDTL = (runway.getRunway().getDTL() / overallLength) * width;
        scaledBlast = (blastProtection / overallLength) * width;
        scaledRESA = (300 / overallLength) * width;
    }

    private void calculateStopwayAndClearwayLengths() {
        closeClearwayLength = (runway2.getRunway().getClearwayLength() / overallLength) * width;
        closeStopwayLength = (runway2.getRunway().getStopwayLength() / overallLength) * width;
        farClearwayLength = (runway.getRunway().getClearwayLength() / overallLength) * width;
        farStopwayLength = (runway.getRunway().getStopwayLength() / overallLength) * width;

        closeClearwayLength -= closeStopwayLength;
        farClearwayLength -= farStopwayLength;

        obPos += scaledDTL + closeStopwayLength + closeClearwayLength;

        ALSUp = 4 * obstacle.getHeight();
        ALSAcross = 50 * ALSUp;
        ALSUp = (ALSUp / overallLength) * height * 10;
        ALSAcross = (ALSAcross / overallLength) * height;
    }

    private void drawBase() {
        gc.clearRect(0, 0, width, height);
        gc.save();
        applyTransformations();
        fillBackground();
    }

    private void applyTransformations() {
        gc.transform(new Affine(new Scale(scale, scale, width / 2, height / 2)));
        gc.transform(new Affine(new Translate(x, y)));
    }

    private void fillBackground() {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, width, height);
    }

    private void drawRunwayFeatures() {
        drawRunwayBase();
    }

    private void drawRunwayBase() {
        gc.setFill(LANDING_STRIP_COLOR);
        gc.fillRect(0, (height - LANDING_STRIP_HEIGHT), width - farStopwayLength - farClearwayLength, LANDING_STRIP_HEIGHT);

        gc.setFill(CLEARWAY_COLOR);
        gc.fillRect(0, (height - LANDING_STRIP_HEIGHT), closeClearwayLength, LANDING_STRIP_HEIGHT);

        gc.setFill(STOPWAY_COLOR);
        gc.fillRect(closeClearwayLength, (height - LANDING_STRIP_HEIGHT), closeStopwayLength, LANDING_STRIP_HEIGHT);

        currentEnd = closeStopwayLength + closeClearwayLength;

        gc.setFill(DTL_COLOR);
        gc.fillRect(currentEnd, (height - LANDING_STRIP_HEIGHT), scaledDTL, LANDING_STRIP_HEIGHT);

        gc.setFill(CLEARWAY_COLOR);
        gc.fillRect(width - farClearwayLength, (height - LANDING_STRIP_HEIGHT), farClearwayLength, LANDING_STRIP_HEIGHT);

        gc.setFill(STOPWAY_COLOR);
        gc.fillRect(width - farClearwayLength - farStopwayLength, (height - LANDING_STRIP_HEIGHT), farStopwayLength, LANDING_STRIP_HEIGHT);
    }

    public void drawDirection() {
        DrawBasedOnDirection drawDirection = new DrawBasedOnDirection(reflected, height, closeStopwayLength, farStopwayLength, closeClearwayLength, farClearwayLength, dToObstacle, runway, ALSUp, scaledBlast, scaledRESA, scaledDTL, gc, obPos, RESALen, sixtyLen, TODALen, TORALen, ASDALen, LDALen, ALSAcross, width, obLen);

        if(state==State.TAKEOFF){
            drawDirection.takeOffView();
        }else{
            drawDirection.landingView();
        }
    }

    public void drawCompass() {
        //TODO: Fix text overlays circle, and north pointer is gone for some reason
        DrawCompass compass = new DrawCompass(runway, gc, height, width, reflected);
        compass.drawCompass();
    }

    //need to add stopway and clearway to the view.
    @Override
    public void draw(RedeclaredRunway runway1, RedeclaredRunway runway2,State state) {
       
        initializeRunwayConfiguration(runway1, runway2, state);
        calculateDimensions();
        drawBase();
        drawRunwayFeatures();
        drawRunwayBase();
        
        ObstacleDrawer obstacleDrawer = new ObstacleDrawer(gc, width, height, reflected, obPos, obLen, obHeight);
        obstacleDrawer.drawObstacle();

        drawDirection();
        gc.restore(); //EVERYTHING PAST THIS POINT WILL NOT BE SCALED

        drawCompass();
    }
}
