package uk.ac.soton.comp2211.components;

public class ObstacleOnRunway extends Obstacle {

protected int position;
protected int distanceFromCentreLine;

    public ObstacleOnRunway(String name, int height, int length, int position, int distanceFromCentreLine) {
        super(name, height, length);
        this.position = position;
        this.distanceFromCentreLine = distanceFromCentreLine;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDFCL() {
        return distanceFromCentreLine;
    }

    public void setDFCL(int distanceFromCentreLine) {
        this.distanceFromCentreLine = distanceFromCentreLine;
    }

}
