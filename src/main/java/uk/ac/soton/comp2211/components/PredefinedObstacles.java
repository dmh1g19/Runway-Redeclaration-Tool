package uk.ac.soton.comp2211.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.ac.soton.comp2211.airport.Obstacle;
import uk.ac.soton.comp2211.utility.XMLUtil;

import java.util.ArrayList;

public class PredefinedObstacles {
    private static final ObservableList<Obstacle> obstacles = FXCollections.observableArrayList();


    public static void init(){
        System.out.println("Hi");
        obstacles.setAll(XMLUtil.readObstacles());
    }

    public static void addObstacle(Obstacle obstacle){
        System.out.println("added Obstacle");
        obstacles.add(obstacle);

    }

    public static ObservableList<Obstacle> getObstacles() {
        return obstacles;
    }
    public static void removeObstacle(int i){
        obstacles.remove(i);
    }

    public static void writeObstaclesToXML(){
        XMLUtil.writeObstacles(new ArrayList<Obstacle>(obstacles.stream().toList()) );
    }
}
