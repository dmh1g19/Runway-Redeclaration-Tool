package uk.ac.soton.comp2211.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.platform.engine.discovery.ClasspathResourceSelector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.Obstacle;
import uk.ac.soton.comp2211.airport.Runway;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class XMLUtil {
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("airports.xml");
        Airport obj1 = (Airport) context.getBean("Airport1");
        Airport obj2 = (Airport) context.getBean("Airport2");
        Runway RW1 =(Runway) context.getBean("Runway1");
        Runway RW2 =(Runway) context.getBean("Runway2");
        Runway RW3 =(Runway) context.getBean("Runway3");
        Runway RW4 =(Runway) context.getBean("Runway4");
        Runway RW5 =(Runway) context.getBean("Runway5");
        Runway RW6 =(Runway) context.getBean("Runway6");
        obj1.getName();
        obj1.getRunways();
        obj2.getName();
        obj2.getRunways();

        RW1.getName();
        RW1.getLength();
        RW1.getWidth();
        RW1.getBearing();
        RW1.getTODA();
        RW1.getASDA();
        RW1.getLDA();
        RW1.getDTL();
        RW1.getClearwayLength();

        RW2.getName();
        RW2.getLength();
        RW2.getWidth();
        RW2.getBearing();
        RW2.getTODA();
        RW2.getASDA();
        RW2.getLDA();
        RW2.getDTL();
        RW2.getClearwayLength();

        RW3.getName();
        RW3.getLength();
        RW3.getWidth();
        RW3.getBearing();
        RW3.getTODA();
        RW3.getASDA();
        RW3.getLDA();
        RW3.getDTL();
        RW3.getClearwayLength();

        RW4.getName();
        RW4.getLength();
        RW4.getWidth();
        RW4.getBearing();
        RW4.getTODA();
        RW4.getASDA();
        RW4.getLDA();
        RW4.getDTL();
        RW4.getClearwayLength();

        RW5.getName();
        RW5.getLength();
        RW5.getWidth();
        RW5.getBearing();
        RW5.getTODA();
        RW5.getASDA();
        RW5.getLDA();
        RW5.getDTL();
        RW5.getClearwayLength();

        RW6.getName();
        RW6.getLength();
        RW6.getWidth();
        RW6.getBearing();
        RW6.getTODA();
        RW6.getASDA();
        RW6.getLDA();
        RW6.getDTL();
        RW6.getClearwayLength();
    }


    /**
     * A fake read obstacles function
     * Please feel free to  edit or delete this one to make it actually read from the xml
     * This just allows me to implement the rest of the predefined obstacle stuff with template data
     * @return an array list of obstacles
     */
    public static ArrayList<Obstacle> readObstacles(){
        Obstacle tree = new Obstacle("Tree",5,50,2);
        Obstacle civilianPlane = new Obstacle("Civilian Small Plane",3,8,5);
        Obstacle smallPlane = new Obstacle("Small Commercial Plane", 11, 30,10);
        Obstacle largePlane = new Obstacle("Large Commercial Plane",19,64,30);
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
        obstacles.add(tree);
        obstacles.add(civilianPlane);
        obstacles.add(smallPlane);
        obstacles.add(largePlane);
        return obstacles;

    }

    /**
     * called by the predefined obstacles class to update the xml file
     * We could have this called automatically using a listener or do it manually with like a save button or something
     * @param obstacles the new list of obstacles
     */
    public static void writeObstacles (ArrayList<Obstacle> obstacles){
        // TODO write obstacles to xml file
    }

    public static Airport[] importAirports(String fileName) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return xmlMapper.readValue(new File(fileName), Airport[].class);
    }

}

