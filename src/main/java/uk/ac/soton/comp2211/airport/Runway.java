package uk.ac.soton.comp2211.airport;

public class Runway {
    protected String name;
    protected int length;
    protected int width;
    protected int bearing;

    protected int TORA;
    protected int TODA;
    protected int ASDA;
    protected int LDA;
    protected int displacedThreshold = 0;

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

    public int getBearing() {
        return bearing;
    }

    public void setBearing(int bearing) {
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

    public Runway(String name, int length, int width, int bearing, int TORA, int TODA, int ASDA, int LDA) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.bearing = bearing;
        this.TORA = TORA;
        this.TODA = TODA;
        this.ASDA = ASDA;
        this.LDA = LDA;
    }
    public Runway(String name, int length, int width, int bearing, int TORA,  int TODA, int ASDA, int LDA , int displacedThreshold) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.bearing = bearing;
        this.TORA = TORA;
        this.TODA = TODA;
        this.ASDA = ASDA;
        this.LDA = LDA;
        this.displacedThreshold = displacedThreshold;
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
        displacedThreshold = runway.getDTL();
    }

    public Runway(Runway runway, ObstacleOnRunway obstacle) {
        this(runway);
        setObstacle(obstacle);
    }
}
