package uk.ac.soton.comp2211.utility;


import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class Calculator {

   public Runway LandingTowardsObstacle (Runway runway, ObstacleOnRunway obs){
       Runway runwayWithObstacle = new Runway(runway);
       int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
       int RESA = 240;
       int distanceToStripEnd = 60;
       int newLDA = obstaclePosition -(RESA + distanceToStripEnd);
       runwayWithObstacle.setLDA(newLDA);


       return runwayWithObstacle;
   }


    public Runway LandingOverObstacle (Runway runway, ObstacleOnRunway obs){
        Runway runwayWithObstacle = new Runway(runway);
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway  the highest point is
        int obstacleLength = obs.getLength();
        int RESA = 240;
        int distanceToStripEnd = 60;
        int obstacleHeight = obs.getHeight();
        //int newLDA = (obstaclePosition -distanceToStripEnd) - Integer.max((RESA + obstacleLength) , (obstacleHeight *50));
        int newLDA = runway.getLength() -obstaclePosition -distanceToStripEnd -  (obstacleHeight *50);
        runwayWithObstacle.setLDA(newLDA);


        return runwayWithObstacle;
    }

    public Runway TakeOffTowardsObstacle (Runway runway, ObstacleOnRunway obs){
        Runway runwayWithObstacle = new Runway(runway);
        int obstacleHeight = obs.getHeight();
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
        int RESA = 240;
        int distanceToStripEnd = 60;
        int clearway = runway.getTODA() -runway.getTORA();
        int stopway = runway.getASDA() - runway.getTORA();
        int newTORA = (obstaclePosition + runway.getDTL()) -(obstaclePosition -distanceToStripEnd) -  (obstacleHeight *50);
        runwayWithObstacle.setTORA(newTORA);
        runwayWithObstacle.setTODA(newTORA+clearway);
        runwayWithObstacle.setASDA(newTORA+stopway);


        return runwayWithObstacle;
    }

    public Runway TakeOffAwayFromObstacle (Runway runway, ObstacleOnRunway obs){
        Runway runwayWithObstacle = new Runway(runway);
        int obstacleHeight = obs.getHeight();
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
        int RESA = 240;
        int distanceToStripEnd = 60;
        int clearway = runway.getTODA() -runway.getTORA();
        int stopway = runway.getASDA() - runway.getTORA();
        int newTORA = obstaclePosition -runway.getDTL() -(obstaclePosition -distanceToStripEnd) -  (obstacleHeight *50);
        runwayWithObstacle.setTORA(newTORA);
        runwayWithObstacle.setTODA(newTORA);
        runwayWithObstacle.setASDA(newTORA);


        return runwayWithObstacle;
    }
}
