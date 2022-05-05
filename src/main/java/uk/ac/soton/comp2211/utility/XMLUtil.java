package uk.ac.soton.comp2211.utility;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.util.Pair;
import org.junit.platform.engine.discovery.ClasspathResourceSelector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.Obstacle;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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






    public static Airport[] importAirports(String fileName) throws IOException {
        File file= new File(fileName);
        return importAirports(file);
    }

    public static Airport[] importAirports(File file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            Airport[] airports =xmlMapper.readValue(file, Airport[].class);
            for (Airport airport : airports){
               if(airport.getRunways()[0].getFirst().getName()==null)
                   throw new IOException();
               return xmlMapper.readValue(file, Airport[].class);
            }
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
       return null;
    }

    public static Obstacle[] importObstacles(String fileName) throws IOException {
        File file = new File(fileName);
        return importObstacles(file);
    }

    public static Obstacle[] importObstacles(File file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Obstacle[] obstacles =xmlMapper.readValue(file, Obstacle[].class);
            for (Obstacle obstacle : obstacles){
                if(obstacle.getName()==null)
                    throw new IOException();
                return obstacles;
            }
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
        return null;
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

    public static void storeCalculation(Airport airport ,Pair<RedeclaredRunway, RedeclaredRunway> runways, File file) throws IOException {
        String toWrite = airport.getName() + "\n\n"
                + runways.getKey().getRunway().getName() + "\n"
                + "TORA: " + runways.getKey().getRunway().getTORA() + "m\n"
                + "TODA: " + runways.getKey().getRunway().getTODA() + "m\n"
                + "ASDA: " + runways.getKey().getRunway().getASDA() + "m\n"
                + "LDA: " + runways.getKey().getRunway().getLDA() + "m\n\n"
                + runways.getValue().getRunway().getName() + "\n"
                + "TORA: " + runways.getValue().getRunway().getTORA() + "m\n"
                + "TODA: " + runways.getValue().getRunway().getTODA() + "m\n"
                + "ASDA: " + runways.getValue().getRunway().getASDA() + "m\n"
                + "LDA: " + runways.getValue().getRunway().getLDA() + "m\n\n"
                + "Obstacle Information" + "\n"
                + "Height: " + runways.getKey().getObstacle().getHeight() + "m\n"
                + "Width: " + runways.getKey().getObstacle().getWidth() + "m\n"
                + "Length: " + runways.getKey().getObstacle().getLength() + "m\n"
                + "Distance From " + runways.getKey().getRunway().getName() + ": " + runways.getKey().getObstacle().getPosition() + "m\n"
                + "Distance From " + runways.getValue().getRunway().getName() + ": " + runways.getValue().getObstacle().getPosition() + "m";

        Files.write(file.toPath(), toWrite.getBytes());
    }
}

