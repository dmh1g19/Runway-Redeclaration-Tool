package uk.ac.soton.comp2211.scene;

import javafx.scene.layout.StackPane;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.components.RunwayView;

public class ViewScene extends BaseScene {

    RunwayView view;

    public ViewScene(App app) {
        super(app);
    }

    @Override
    public void build() {
        root = new StackPane();

        root.getChildren().add(view);
    }

    public void setView(RunwayView view) {
        this.view = view;
    }

    public RunwayView getView() {
        return view;
    }

}
