package uk.ac.soton.comp2211.airport;

import uk.ac.soton.comp2211.airport.Obstacle;

public class ObstacleOnRunway extends Obstacle {

protected int position; // how far down the runway the obstacle is
protected int distanceFromCentreLine;

    public ObstacleOnRunway () {

    }

    public ObstacleOnRunway(Obstacle obstacle, int pos, int dis) {
        this.name = obstacle.getName();
        this.height = obstacle.getHeight();
        this.length = obstacle.getLength();
        this.width = obstacle.getWidth();
        this.position = pos;
        this.distanceFromCentreLine = dis;
    }

    public ObstacleOnRunway(String name, int height, int length, int width, int position, int distanceFromCentreLine) {
        super(name, height, length, width);
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
