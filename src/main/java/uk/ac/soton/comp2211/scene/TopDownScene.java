package uk.ac.soton.comp2211.scene;

import javafx.scene.layout.StackPane;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.components.TopDownRunway;

public class TopDownScene extends BaseScene {

    public TopDownScene(App stage) {
        super(stage);
    }

    @Override
    public void build() {
        root = new StackPane();

        Runway runway = new Runway("Runway01",4500,50,18,3902,3902,3902,3595, 5);
        ObstacleOnRunway obstacle = new ObstacleOnRunway("Box",0,50,0, 0);

        TopDownRunway top = new TopDownRunway(runway,obstacle,app.getScene().getWidth(),app.getScene().getHeight(), "left");

        root.getChildren().add(top);

        root.setStyle("-fx-background-color: #81c483");
    }
}
