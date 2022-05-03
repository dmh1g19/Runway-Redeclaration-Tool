package uk.ac.soton.comp2211.utility;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import java.util.List;

public class XMLUtil {

    /**
     * A fake read obstacles function
     * Please feel free to  edit or delete this one to make it actually read from the xml
     * This just allows me to implement the rest of the predefined obstacle stuff with template data
     * @return an array list of obstacles
     */
    public static ArrayList<Obstacle> readObstacles(){
        XmlMapper xmlMapper = new XmlMapper();

        try {
            XmlMapper mapper = new XmlMapper();

            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            Obstacle[] obs = mapper.readValue(new File("obstacles.xml"), Obstacle[].class);
            System.out.println("Good");
            return new ArrayList<>(List.of(obs));

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error");
        }

        return null;
    }

    /**
     * called by the predefined obstacles class to update the xml file
     * We could have this called automatically using a listener or do it manually with like a save button or something
     * @param obstacles the new list of obstacles
     */
    public static void writeObstacles (ArrayList<Obstacle> obstacles){
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            xmlMapper.writeValue(new File("obstacles.xml"), obstacles.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void exportAirport(Airport airport) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            xmlMapper.writeValue(new File("obstacles.xml"), airport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Airport[] importAirports(String fileName) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return xmlMapper.readValue(new File(fileName), Airport[].class);
    }

    public static Airport[] importAirports(File file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return xmlMapper.readValue(file, Airport[].class);
    }

    public static Obstacle[] importObstacles(String fileName) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return xmlMapper.readValue(new File(fileName), Obstacle[].class);
    }

    public static Obstacle[] importObstacles(File file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return xmlMapper.readValue(file, Obstacle[].class);
    }

    public static void exportAirports(File file, List<Airport> airports) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        xmlMapper.writeValue(file, airports);
    }

    public static void exportObstacles(File file, List<Obstacle> obstacles) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        xmlMapper.writeValue(file, obstacles);
    }
}

