package uk.ac.soton.comp2211.utility;


import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

public class Calculator {

   public Runway LandingTowardsObstacle (Runway runway, ObstacleOnRunway obs){
       Runway runwayWithObstacle = new Runway(runway);
       int obstaclePosition = obs.getPosition();
       int RESA = 240;
       int distanceToStripEnd = 60;
       int newLDA = obstaclePosition -(RESA + distanceToStripEnd);
       runwayWithObstacle.setLDA(newLDA);


       return runwayWithObstacle;
   }

}
