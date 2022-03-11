package uk.ac.soton.comp2211.utility;


import uk.ac.soton.comp2211.airport.Obstacle;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;

import java.util.HashMap;
import java.util.Map;

public class Calculator {
    static int RESA = 240;
    static int distanceToStripEnd = 60;
    static int blastProtectionDistance = 300;

    public static int getBlastProtectionDistance() {
        return blastProtectionDistance;
    }

    public static int getDistanceToStripEnd() {
        return distanceToStripEnd;
    }

    public static int getRESA() {
        return RESA;
    }

    public static void setBlastProtectionDistance(int blastProtectionDistance) {
        Calculator.blastProtectionDistance = blastProtectionDistance;
    }

    public static void setRESA(int RESA) {
        Calculator.RESA = RESA;
    }

    public static void setDistanceToStripEnd(int distanceToStripEnd) {
        Calculator.distanceToStripEnd = distanceToStripEnd;
    }

    /**
     * Calculates runway landing parameters if the plane is landing towards the obstacle
     * @param runway the runway the plane is landing on
     * @param obs the obstacle on the runway
     * @returns updated runway values
     */
    public static Runway LandingTowardsObstacle (Runway runway, ObstacleOnRunway obs) throws IncorrectObstacleException {
        validateObstacle(obs);
       Runway runwayWithObstacle = new Runway(runway);
       int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
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
    public static Runway LandingOverObstacle (Runway runway, ObstacleOnRunway obs) throws IncorrectObstacleException {
        validateObstacle(obs);
        Runway runwayWithObstacle = new Runway(runway);
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway  the highest point is
        int obstacleLength = obs.getLength();
        int obstacleHeight = obs.getHeight();
        int newLDA = (obstaclePosition -distanceToStripEnd) - Integer.max((RESA + obstacleLength) , (obstacleHeight *50));
        runwayWithObstacle.setLDA(newLDA);


        return runwayWithObstacle;
    }

    /**
     * Calculates take off parameters if the plane is taking off towards the obstacle
     * @param runway the runway the plane is landing on
     * @param obs the obstacle on the runway
     * @returns updated runway values
     */
    public static Runway TakeOffAwayFromObstacle (Runway runway, ObstacleOnRunway obs) throws IncorrectObstacleException {
        validateObstacle(obs);
        Runway runwayWithObstacle = new Runway(runway);
        int obstacleHeight = obs.getHeight();
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
        int clearway = runway.getTODA() -runway.getTORA();
        int stopway = runway.getASDA() - runway.getTORA();
        int newTORA = runway.getTORA() -blastProtectionDistance - obstaclePosition - runway.getDTL() ;
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
    public static Runway TakeOffTowardsObstacle (Runway runway, ObstacleOnRunway obs) throws IncorrectObstacleException {
        validateObstacle(obs);
        Runway runwayWithObstacle = new Runway(runway);
        int obstacleHeight = obs.getHeight();
        int obstaclePosition = obs.getPosition();//obstacle position should represent how far down the runway it is
        int newTORA = obstaclePosition + runway.getDTL() - distanceToStripEnd - Integer.max((RESA + obs.getLength()) , (obstacleHeight *50));

        runwayWithObstacle.setTORA(newTORA);
        runwayWithObstacle.setTODA(newTORA);
        runwayWithObstacle.setASDA(newTORA);


        return runwayWithObstacle;
    }

    /**
     * Performs all the calculations if the planes are taking off and landing towards the obstacle
     * @param runway the runway for the calculations to be performed on
     * @param obs the obstacle on the runway
     * @return a runway with updated parameters
     * @throws IncorrectObstacleException
     */
    public static   Runway TowardsObstacle (Runway runway, ObstacleOnRunway obs) throws IncorrectObstacleException {
        validateObstacle(obs);
        return LandingTowardsObstacle(TakeOffTowardsObstacle(runway ,obs),obs);

    }

    /**
     * Performs all the calculations if the planes are taking off and landing away from the obstacle
     * @param runway the runway for the calculations to be performed on
     * @param obs the obstacle on the runway
     * @return a runway with updated parameters
     * @throws IncorrectObstacleException
     */
    public  static Runway AwayFromObstacle (Runway runway, ObstacleOnRunway obs) throws IncorrectObstacleException {
        validateObstacle(obs);
        return LandingOverObstacle(TakeOffAwayFromObstacle(runway ,obs),obs);

    }

    /**
     * Checks to make sure the obstacle provided has been given valid parameters
     * @param obs the obstacle to be checked
     * @throws IncorrectObstacleException
     */
    private static void validateObstacle (ObstacleOnRunway obs) throws IncorrectObstacleException {


        if (obs.getHeight() < 0 || obs.getHeight() > 10000 ){
            throw new IncorrectObstacleException("Height of obstacle incorrect");
        }
        if (obs.getPosition() < -10000 || obs.getPosition() > 10000 ){
            throw new IncorrectObstacleException("Position of obstacle incorrect");
        }
    }

    /**
     * Provides a hashmap storing the calculations breakdown
     * @param runway the runway for the calculations to be performed on
     * @param obs the obstacle on the runway
     * @param isTowards is the plane going towards the obstacle (used to decided which functions to use from calculater)
     * @return returns a hashmap where  the keys are mapped to their calculations
     */
    public static Map<String, String> calculationBreakdown (Runway runway, ObstacleOnRunway obs , boolean isTowards){
        Map<String, String>  map = new HashMap<>();
        String[] names = {"TORA","TODA","ASDA"};
        if(isTowards){

            for (String name : names){
                map.put(name,TOTCalculationBuilder(name,runway,obs));
            }
            int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway  the highest point is
            int RESA = 240;
            int distanceToStripEnd = 60;
            int newLDA = obstaclePosition -(RESA + distanceToStripEnd);
            StringBuilder s = new StringBuilder();
            s.append("LDA= Distance From Threshold - Strip End - RESA\n");
            s.append("   = "+ obs.getPosition() + " - 60 - 240 \n");
            s.append("   = "+newLDA);
            map.put("LDA",s.toString());


        }else{
            for (int i = 0; i < 3; i++) {
               map.put(names[i],TOACalculationBuilder(names[i],runway,obs,i));
            }
            int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway  the highest point is
            int obstacleLength = obs.getLength();
            int RESA = 240;
            int distanceToStripEnd = 60;
            int obstacleHeight = obs.getHeight();
            //int newLDA = (obstaclePosition -distanceToStripEnd) - Integer.max((RESA + obstacleLength) , (obstacleHeight *50));
            int newLDA = runway.getLDA() -obstaclePosition -distanceToStripEnd -  (obstacleHeight *50);
            StringBuilder s = new StringBuilder();
            s.append("LDA = Original LDA - Distance from Threshold – Strip End - Slope Calculation\n");
            s.append("     = "+runway.getLDA() +" - "+ obs.getPosition() + " - 60 " + "- ( 50 * " +obstacleHeight+ ")\n");
            s.append("     = "+newLDA);
            map.put("LDA",s.toString());
        }
        return map;
    }

    /**
     * Builds a string (calculation) to show the calculations breakdown for each variable affected by taking off towards the obstacle
     * @param name the name of the variable we are creating an entry for in the hashmap eg TODA TORA ASDA
     * @param runway the runway the calculations are being performed on
     * @param obs the obstacle on the runway
     * @return the calculations breakdown for that variable
     */
    private static  String TOTCalculationBuilder( String name , Runway runway , ObstacleOnRunway obs){
        int obstacleHeight = obs.getHeight();
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
        int RESA = 240;
        int distanceToStripEnd = 60;
        int newTORA = obstaclePosition + runway.getDTL() - distanceToStripEnd - (obstacleHeight *50);

        StringBuilder s = new StringBuilder();
        s.append(name+"= Distance from Threshold + Displaced Threshold - Slope Calculation - Strip End \n");
        s.append("    = "+obs.getPosition() +" + "+runway.getDTL() + " - 60 " + "- ( 50 * " +obstacleHeight+ ") \n");
        s.append("    = "+newTORA);
        return  s.toString();
    }

    /**
     * Builds a string (calculation) to show the calculations breakdown for each variable affected by taking off towards the obstacle
     * @param name the name of the variable we are creating an entry for in the hashmap eg TODA TORA ASDA
     * @param runway the runway the calculations are being performed on
     * @param obs the obstacle on the runway
     * @param extra if we are including stopway or clearway  default = nothing 1 = clearway 2 = stopway
     * @return the calculations breakdown for that variable
     */
    private static  String TOACalculationBuilder( String name , Runway runway , ObstacleOnRunway obs, int extra) {
        int obstaclePosition = obs.getPosition(); //obstacle position should represent how far down the runway it is
        int clearway = runway.getTODA() - runway.getTORA();
        int stopway = runway.getASDA() - runway.getTORA();
        int blastProtection = 300;
        int newTORA = runway.getTORA() - (Integer.max(300,blastProtection)) - obstaclePosition - runway.getDTL();
        StringBuilder s = new StringBuilder();
        s.append(name+"= Original TORA - (Max of Blast Protection and (RESA+srip end)) - Distance from Threshold - Displaced Threshold");
        switch (extra) {
            case 1 -> s.append("+ Clearway \n");
            case 2 -> s.append("+ Stopway \n");
            default -> {
                s.append("\n");

            }
        }
        s.append("    = "+runway.getTORA() +" - "+ (Integer.max(300,blastProtection)) + " - " + obs.getPosition() + " - "+ runway.getDTL() );
        switch (extra) {
            case 1 -> s.append(clearway+" \n").append("    +"+(newTORA+clearway));
            case 2 -> s.append(stopway+ "\n").append("    +"+(newTORA+stopway));
            default -> {
                s.append("\n").append("    +"+newTORA);

            }
        }
        return s.toString();
    }




    public static class IncorrectObstacleException extends Exception {
        public IncorrectObstacleException(String errorMessage) {
            super(errorMessage);
        }
    }


}

