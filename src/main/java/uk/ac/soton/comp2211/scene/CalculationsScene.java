package uk.ac.soton.comp2211.scene;

import javafx.scene.layout.*;
import javafx.util.Pair;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.components.SideOnRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;

import java.util.Stack;

public class CalculationsScene extends BaseScene {

    public CalculationsScene(App stage) {
        super(stage);
    }

    @Override
    public void build() {
        root = new AnchorPane();

        //Side view test values
        Runway runway = new Runway("Runway01",7000,2500,18,3902,3902,3902,3595, new Pair<>(0,0));
        ObstacleOnRunway obstacle = new ObstacleOnRunway("Box",160,100,2000, 40);
        SideOnRunway sideOn = new SideOnRunway(runway, obstacle,250,200);

        AnchorPane.setRightAnchor(sideOn, 0d);
        AnchorPane.setTopAnchor(sideOn, 0d);
        root.getChildren().add(sideOn);

        root.setStyle("-fx-background-color: #81c483");
    }
}
