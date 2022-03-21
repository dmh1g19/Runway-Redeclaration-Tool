package uk.ac.soton.comp2211.views;

import javafx.scene.Group;
import javafx.scene.Parent;
import uk.ac.soton.comp2211.components.RunwayView;

public class ViewsView extends BaseView {

    //View Nodes
    private RunwayView runwayView;

    public RunwayView getRunwayView() {
        return runwayView;
    }

    public ViewsView() {
        view = createView();
    }

    public ViewsView(RunwayView runwayView) {
        this.runwayView = runwayView;
        view = createView();
    }

    @Override
    public Parent createView() {
        return new Group(runwayView);
    }

    public void setRunwayView(RunwayView runwayView) {
        this.runwayView = runwayView;
    }

}
