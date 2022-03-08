package uk.ac.soton.comp2211.scene;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.util.Pair;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.components.SideOnRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.components.TopDownRunway;

public class CalculationsScene extends BaseScene {

    public CalculationsScene(App stage) {
        super(stage);
    }

    @Override
    public void build() {
        root = new AnchorPane();

        //test values
        Runway runway = new Runway("Runway01",4500,50,18,3902,3902,3902,3595, new Pair<>(0,0));
        ObstacleOnRunway obstacle = new ObstacleOnRunway("Box",10,10,1500, 0);

        SideOnRunway sideOn = new SideOnRunway(runway, obstacle,400,200, "forward");
        AnchorPane.setRightAnchor(sideOn, 0d);
        AnchorPane.setTopAnchor(sideOn, 0d);

        TopDownRunway top = new TopDownRunway(runway,obstacle,4500,200);
        ScrollPane s1 = new ScrollPane(top);
        s1.setPrefSize(400, 200);
        s1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        s1.setFitToWidth(true);
        s1.setFitToHeight(true);
        AnchorPane.setRightAnchor(s1, 0d);
        AnchorPane.setBottomAnchor(s1, 0d);

        root.getChildren().addAll(sideOn,s1);

        root.setStyle("-fx-background-color: #81c483");
    }
}
