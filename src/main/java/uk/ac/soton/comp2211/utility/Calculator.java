package uk.ac.soton.comp2211.utility;


import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class Calculator {

    /**
     * Calculates runway landing parameters if the plane is landing towards the obstacle
     * @param runway the runway the plane is landing on
     * @param obs the obstacle on the runway
     * @returns updated runway values
     */
    public static Runway LandingTowardsObstacle (Runway runway, ObstacleOnRunway obs){
       Runway runwayWithObstacle = new Runway(runway);
       int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
       int RESA = 240;
       int distanceToStripEnd = 60;
       int newLDA = obstaclePosition -(RESA + distanceToStripEnd);
       runwayWithObstacle.setLDA(newLDA);


       return runwayWithObstacle;
   }

    /**
     * Calculates runway landing parameters if the plane is landing over the obstacle
     * @param runway the runway the plane is landing on
     * @param obs the obstacle on the runway
     * @returns updated runway values
     */
    public static Runway LandingOverObstacle (Runway runway, ObstacleOnRunway obs){
        Runway runwayWithObstacle = new Runway(runway);
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway  the highest point is
        int obstacleLength = obs.getLength();
        int RESA = 240;
        int distanceToStripEnd = 60;
        int obstacleHeight = obs.getHeight();
        //int newLDA = (obstaclePosition -distanceToStripEnd) - Integer.max((RESA + obstacleLength) , (obstacleHeight *50));
        int newLDA = runway.getLDA() -obstaclePosition -distanceToStripEnd -  (obstacleHeight *50);
        runwayWithObstacle.setLDA(newLDA);


        return runwayWithObstacle;
    }

    /**
     * Calculates take off parameters if the plane is taking off towards the obstacle
     * @param runway the runway the plane is landing on
     * @param obs the obstacle on the runway
     * @returns updated runway values
     */
    public static Runway TakeOffAwayFromObstacle (Runway runway, ObstacleOnRunway obs){
        Runway runwayWithObstacle = new Runway(runway);
        int obstacleHeight = obs.getHeight();
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
        int clearway = runway.getTODA() -runway.getTORA();
        int stopway = runway.getASDA() - runway.getTORA();
        int newTORA = runway.getTORA() -300 - obstaclePosition - runway.getDTL() ;
        runwayWithObstacle.setTORA(newTORA);
        runwayWithObstacle.setTODA(newTORA+clearway);
        runwayWithObstacle.setASDA(newTORA+stopway);


        return runwayWithObstacle;
    }
    /**
     * Calculates take off parameters if the plane is taking off away from an obstacle
     * @param runway the runway the plane is landing on
     * @param obs the obstacle on the runway
     * @returns updated runway values
     */
    public static Runway TakeOffTowardsObstacle (Runway runway, ObstacleOnRunway obs){
        Runway runwayWithObstacle = new Runway(runway);
        int obstacleHeight = obs.getHeight();
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
        int RESA = 240;
        int distanceToStripEnd = 60;
        int newTORA = obstaclePosition + runway.getDTL() - distanceToStripEnd - (obstacleHeight *50);

        runwayWithObstacle.setTORA(newTORA);
        runwayWithObstacle.setTODA(newTORA);
        runwayWithObstacle.setASDA(newTORA);


        return runwayWithObstacle;
    }
}
