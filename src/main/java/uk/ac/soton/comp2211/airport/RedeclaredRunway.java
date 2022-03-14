package uk.ac.soton.comp2211.airport;

public class RedeclaredRunway {

    protected final Runway runway;
    protected final ObstacleOnRunway obstacle;
    protected final Direction direction;

    public RedeclaredRunway(Runway runway, ObstacleOnRunway obstacle, Direction direction) {
        this.runway = runway;
        this.obstacle = obstacle;
        this.direction = direction;
    }

    public Runway getRunway() {
        return runway;
    }

    public ObstacleOnRunway getObstacle() {
        return obstacle;
    }

    public Direction getDirection() {
        return direction;
    }

}
