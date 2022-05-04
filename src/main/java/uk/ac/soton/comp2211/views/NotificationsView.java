package uk.ac.soton.comp2211.views;

import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NotificationsView extends BaseView {

    // View Nodes

    public NotificationsView() {
        view = createView();
    }


    @Override
    public Parent createView() {

        TextFlow notifFlow = new TextFlow();
        notifFlow.getChildren().add(new Text("Testing"));


        return notifFlow;
    }
}
