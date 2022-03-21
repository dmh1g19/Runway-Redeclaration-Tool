package uk.ac.soton.comp2211.scene;

import javafx.scene.layout.StackPane;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.Direction;
import uk.ac.soton.comp2211.airport.ObstacleOnRunway;
import uk.ac.soton.comp2211.airport.RedeclaredRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.components.TopDownRunway;

public class TopDownScene extends BaseScene {

    public TopDownScene(App stage) {
        super(stage);
    }
    private TopDownRunway top;

    @Override
    public void build() {
        root = new StackPane();

        Runway runway = new Runway("Runway01",0,0,0,0,0,0,0, 0);
        ObstacleOnRunway obstacle = new ObstacleOnRunway("Box",0,0,0, 0, 0);
        runway.setObstacle(obstacle);

        top = new TopDownRunway(app.getScene().getWidth(), app.getScene().getHeight());

        root.getChildren().add(top);
        //root.getChildren().add(top.getRunwayCanvas());
        //root.getChildren().add(top.getUICanvas());

        root.setStyle("-fx-background-color: #81c483");
    }

    public TopDownRunway getView() {
        return top;
    }
}
