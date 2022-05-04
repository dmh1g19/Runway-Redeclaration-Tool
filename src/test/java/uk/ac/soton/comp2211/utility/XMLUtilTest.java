package uk.ac.soton.comp2211.utility;

import javafx.util.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.soton.comp2211.airport.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XMLUtilTest {
    static Obstacle[] savedObstacles;
    static Airport[] savedAirports;

    @BeforeAll
    static  void saveObstacles() throws IOException {
        savedObstacles = XMLUtil.importObstacles("obstacles.xml");
        savedAirports = XMLUtil.importAirports("airports.xml");

    }
    @AfterAll
    static void rewriteObs() throws IOException {
        XMLUtil.exportObstacles(new File("obstacles.xml") ,new ArrayList<>(List.of(savedObstacles)));
        XMLUtil.exportAirports(new File("airports.xml"),new ArrayList<>(List.of(savedAirports)));
    }

    @Test
    void readAndWriteObstaclesString() {
        boolean failed= true;
        try {
            Obstacle obs = new Obstacle("test", 12, 0, 0);
            ArrayList<Obstacle> obstacles = new ArrayList<>();
            obstacles.add(obs);
            XMLUtil.exportObstacles(new File("obstacles.xml"),obstacles);
            Obstacle obs2 = XMLUtil.importObstacles("obstacles.xml")[0];
            assertTrue(obs.getName().equals(obs2.getName()),"Error with reading and writing obstacle name");
            assertSame(obs.getHeight(),obs2.getHeight(),"Error with reading and writing obstacle height");
            assertSame(obs.getLength(),obs2.getLength(),"Error with reading and writing obstacle length");
            assertSame(obs.getWidth(),obs2.getWidth(),"Error with reading and writing obstacle width");
            failed =false;

        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
        assertFalse(failed,"Error reading and writing to obstacles.xml using String filename");


    }

    @Test
    void readAndWriteObstaclesFile() {
        boolean failed= true;
        try {
            Obstacle obs = new Obstacle("test", 12, 0, 0);
            ArrayList<Obstacle> obstacles = new ArrayList<>();
            obstacles.add(obs);
            XMLUtil.exportObstacles(new File("obstacles.xml"),obstacles);
            Obstacle obs2 = XMLUtil.importObstacles(new File("obstacles.xml"))[0];
            assertTrue(obs.getName().equals(obs2.getName()),"Error with reading and writing obstacle name");
            assertSame(obs.getHeight(),obs2.getHeight(),"Error with reading and writing obstacle height");
            assertSame(obs.getLength(),obs2.getLength(),"Error with reading and writing obstacle length");
            assertSame(obs.getWidth(),obs2.getWidth(),"Error with reading and writing obstacle width");
            failed =false;
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
        assertFalse(failed,"Error reading and writing to obstacles.xml using File");
    }

    @Test
    void readAndWriteAirportsString() {

        boolean failed= true;
        try {
             Runway r09L = new Runway("09L",3901,50,089.67,3901,3901,3901,3592,0,309);
              Runway r27R = new Runway("27R", 3901,50, 269.71,3882,3960,3882,3882, 78, 0);
            Runway r09R = new Runway("09R", 3658,50, 089.67,3658,3658,3658,3350,0,308);
            Runway r27L = new Runway("27L", 3658,50, 0269.71,3658,3658,3658,3658,0,0);

            PhysicalRunway r09L27R = new PhysicalRunway(r09L,r27R);
            PhysicalRunway r09R27L = new PhysicalRunway(r09R,r27L);
            PhysicalRunway[] runways = new PhysicalRunway[]{r09L27R,r09R27L};
            Airport heathrow = new Airport("Heathrow Test", runways);

            XMLUtil.exportAirports(new File("airports.xml"), Collections.singletonList(heathrow));
            assertTrue("Heathrow Test".equals(XMLUtil.importAirports("airports.xml")[0].getName()));
            failed =false;
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
        assertFalse(failed,"Error reading and writing to obstacles.xml using File");
    }

    @Test
    void readAndWriteAirportsFile() {

        boolean failed= true;
        try {
            Runway r09L = new Runway("09L",3901,50,089.67,3901,3901,3901,3592,0,309);
            Runway r27R = new Runway("27R", 3901,50, 269.71,3882,3960,3882,3882, 78, 0);
            Runway r09R = new Runway("09R", 3658,50, 089.67,3658,3658,3658,3350,0,308);
            Runway r27L = new Runway("27L", 3658,50, 0269.71,3658,3658,3658,3658,0,0);

            PhysicalRunway r09L27R = new PhysicalRunway(r09L,r27R);
            PhysicalRunway r09R27L = new PhysicalRunway(r09R,r27L);
            PhysicalRunway[] runways = new PhysicalRunway[]{r09L27R,r09R27L};
            Airport heathrow = new Airport("Heathrow Test", runways);

            XMLUtil.exportAirports(new File("airports.xml"), Collections.singletonList(heathrow));
            assertTrue("Heathrow Test".equals(XMLUtil.importAirports(new File("airports.xml"))[0].getName()));
            failed =false;
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
        assertFalse(failed,"Error reading and writing to obstacles.xml using File");
    }



    @Test
    void storeCalculation() {
        boolean failed= true;

        try {
            Runway r09L = new Runway("09L",3901,50,089.67,3901,3901,3901,3592,0,309);
            Runway r27R = new Runway("27R", 3901,50, 269.71,3882,3960,3882,3882, 78, 0);
            Runway r09R = new Runway("09R", 3658,50, 089.67,3658,3658,3658,3350,0,308);
            Runway r27L = new Runway("27L", 3658,50, 0269.71,3658,3658,3658,3658,0,0);

            PhysicalRunway r09L27R = new PhysicalRunway(r09L,r27R);
            PhysicalRunway r09R27L = new PhysicalRunway(r09R,r27L);
            PhysicalRunway[] runways = new PhysicalRunway[]{r09L27R,r09R27L};
            Airport heathrow = new Airport("Heathrow Test", runways);
            ObstacleOnRunway obs = new ObstacleOnRunway("test", 12, 0, 0, -50, 0);
            ObstacleOnRunway obs2 = new ObstacleOnRunway("test", 12, 0, 0, 3646, 0);
            RedeclaredRunway redeclaredRunway1 = Calculator.AwayFromObstacle(r09L,obs);
            RedeclaredRunway redeclaredRunway2 = Calculator.TowardsObstacle(r27R,obs2);
            XMLUtil.storeCalculation(heathrow,new Pair<>(redeclaredRunway1,redeclaredRunway2),new File("calcStoreTest.txt"));
            failed =false;
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }


        assertFalse(failed,"Error storing calculations to file");


    }

}