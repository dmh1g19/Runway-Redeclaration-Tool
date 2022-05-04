package uk.ac.soton.comp2211.views;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NotificationsView extends BaseView {

    // View Nodes
    TextFlow notifFlow = new TextFlow();
    ScrollPane scrollPane = new ScrollPane();

    public NotificationsView() {
        view = createView();
    }

    @Override
    public Parent createView() {
        scrollPane.setContent(notifFlow);
        scrollPane.widthProperty().addListener(e -> {
            notifFlow.setPrefWidth(scrollPane.getWidth() - 15);
        });
        notifFlow.setStyle("-fx-padding: 4");
        return scrollPane;
    }

    public TextFlow getNotifFlow() {
        return notifFlow;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
