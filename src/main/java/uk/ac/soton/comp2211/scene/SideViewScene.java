package uk.ac.soton.comp2211.scene;

import javafx.scene.layout.StackPane;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.components.SideOnRunway;

public class SideViewScene extends BaseScene {

    public SideViewScene (App stage) {
        super(stage);
    }

    @Override
    public void build() {
        root = new StackPane();

        Runway runway = new Runway("Runway01",3500,35,25,2602,1902,1902,1595, 10);
        ObstacleOnRunway obstacle = new ObstacleOnRunway("Box",10,100,2000, 15);

        SideOnRunway sideOn = new SideOnRunway(runway, obstacle,app.getScene().getWidth(), app.getScene().getHeight());

        root.getChildren().add(sideOn);

        root.setStyle("-fx-background-color: #81c483");

    }
}
