package uk.ac.soton.comp2211.airport;

import java.util.Map;


public class Runway {
    protected String name;
    protected int length;
    protected int width;
    protected double bearing;

    protected int TORA;
    protected int TODA;
    protected int ASDA;
    protected int LDA;
    protected int displacedThreshold = 0;
    protected int clearwayLength;
    protected ObstacleOnRunway obstacle;
    protected boolean obstacleOnRunway = false;

    public ObstacleOnRunway getObstacle() {
        return obstacle;
    }

    public void setObstacle(ObstacleOnRunway obstacle) {
        this.obstacle = obstacle;
        obstacleOnRunway = true;
    }

    public void changeObstacle(ObstacleOnRunway obstacle) {
        this.obstacle = obstacle;
        obstacleOnRunway = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public int getTORA() {
        return TORA;
    }

    public void setTORA(int TORA) {
        this.TORA = TORA;
    }

    public int getTODA() {
        return TODA;
    }

    public void setTODA(int TODA) {
        this.TODA = TODA;
    }

    public int getASDA() {
        return ASDA;
    }

    public void setASDA(int ASDA) {
        this.ASDA = ASDA;
    }

    public int getLDA() {
        return LDA;
    }

    public void setLDA(int LDA) {
        this.LDA = LDA;
    }

    public void setDTL(int displacedThreshold) {
        this.displacedThreshold = displacedThreshold;
    }

    public int getDTL() {
        return displacedThreshold;
    }

    public int getClearwayLength() {
        return clearwayLength;
    }

    public void setClearwayDimensions(int clearwayLength) {
        this.clearwayLength = clearwayLength;
    }



    public Runway(String name, int length, int width, double bearing, int TORA, int TODA, int ASDA, int LDA, int clearwayLength) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.bearing = bearing;
        this.TORA = TORA;
        this.TODA = TODA;
        this.ASDA = ASDA;
        this.LDA = LDA;
        this.clearwayLength =clearwayLength;
    }
    public Runway(String name, int length, int width, double bearing, int TORA,  int TODA, int ASDA, int LDA , int clearwayLength , int displacedThreshold) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.bearing = bearing;
        this.TORA = TORA;
        this.TODA = TODA;
        this.ASDA = ASDA;
        this.LDA = LDA;
        this.displacedThreshold = displacedThreshold;
        this.clearwayLength = clearwayLength;
    }
    public Runway (Runway runway){
        name = runway.getName();
        length = runway.getLength();
        width = runway.getWidth();
        bearing = runway.getBearing();
        TORA = runway.getTORA();
        TODA = runway.getTODA();
        ASDA = runway.getASDA();
        LDA = runway.getLDA();
        clearwayLength = runway.getClearwayLength();
        displacedThreshold = runway.getDTL();
    }

    public Runway(Runway runway, ObstacleOnRunway obstacle) {
        this(runway);
        setObstacle(obstacle);
    }
}
