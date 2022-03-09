package uk.ac.soton.comp2211.scene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import uk.ac.soton.comp2211.App;

public class CalculationsScene extends BaseScene {

    public CalculationsScene(App stage) {
        super(stage);
    }

    @Override
    public void build() {
        root = new StackPane();

        Button goToTopView = new Button("Top View");
        goToTopView.setOnMouseClicked(e -> app.loadTopDown());
        Button goToSideView = new Button("Side View");
        goToSideView.setOnMouseClicked(e -> app.loadSide());

        VBox buttonContainer = new VBox();
        buttonContainer.getChildren().addAll(goToSideView,goToTopView);

        buttonContainer.setSpacing(50);
        buttonContainer.setAlignment(Pos.CENTER);
        root.getChildren().addAll(buttonContainer);

        root.setStyle("-fx-background-color: #81c483");
    }
}
