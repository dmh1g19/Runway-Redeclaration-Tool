package uk.ac.soton.comp2211.views;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import uk.ac.soton.comp2211.airport.Direction;
import uk.ac.soton.comp2211.airport.Obstacle;
import uk.ac.soton.comp2211.airport.PhysicalRunway;

import java.awt.*;


public class CalculationsView extends BaseView {

    //View Nodes
    private ComboBox<PhysicalRunway> runwaySelect = new ComboBox<>();
    private ComboBox<Obstacle> obstacleSelect = new ComboBox<>();

    private TextField obstacleHeight = new TextField();
    private TextField obstacleWidth = new TextField();
    private TextField obstacleLength = new TextField();

    private Label lowerThreshold = new Label("THR");
    private TextField distanceLowerThreshold = new TextField();
    private ComboBox<Direction> sectionLowerThreshold = new ComboBox<>();

    private Label upperThreshold = new Label("THR");
    private TextField distanceUpperThreshold = new TextField();
    private ComboBox<Direction> sectionUpperThreshold = new ComboBox<>();

    private Button calculateButton = new Button("Calculate");

    private Button backButton = new Button("Back");

    public ComboBox<PhysicalRunway> getRunwaySelect() {
        return runwaySelect;
    }

    public ComboBox<Obstacle> getObstacleSelect() {
        return obstacleSelect;
    }

    public TextField getObstacleHeight() {
        return obstacleHeight;
    }

    public TextField getObstacleWidth() {
        return obstacleWidth;
    }

    public TextField getObstacleLength() {
        return obstacleLength;
    }

    public Label getLowerThreshold() {
        return lowerThreshold;
    }

    public TextField getDistanceLowerThreshold() {
        return distanceLowerThreshold;
    }

    public ComboBox<Direction> getSectionLowerThreshold() {
        return sectionLowerThreshold;
    }

    public Label getUpperThreshold() {
        return upperThreshold;
    }

    public TextField getDistanceUpperThreshold() {
        return distanceUpperThreshold;
    }

    public ComboBox<Direction> getSectionUpperThreshold() {
        return sectionUpperThreshold;
    }

    public Button getCalculateButton() {
        return calculateButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public CalculationsView() {
        view = createView();
    }

    public Parent createView() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(8));

        layout.setLeft(createInputForm());
        layout.setCenter(createViewButtons());
        layout.setTop(createBackButton());

        return layout;
    }

    public Node createBackButton () {
        BorderPane.setMargin(backButton, new Insets(0,0,8,0));

        return backButton;
    }

    public Node createViewButtons() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(24);

        Button sideOnView = new Button("Side On View");
        sideOnView.getStyleClass().add("viewButton");
        Button topDownView = new Button("Top Down View");
        topDownView.getStyleClass().add("viewButton");

        vbox.getChildren().addAll(sideOnView, topDownView);

        return vbox;
    }

    public Node createInputForm() {
        GridPane inputForm = new GridPane();
        inputForm.getStyleClass().add("inputs");

        //Title
        Label headerLabel = new Label("Calculations");
        headerLabel.getStyleClass().add("headerCalc");
        VBox vbox = new VBox(headerLabel, new Separator(Orientation.HORIZONTAL));
        inputForm.add(vbox,0,0,2,1);

        //Physical Runway Label
        Label runwayLabel = new Label("Runway:");
        inputForm.add(runwayLabel, 0, 1);

        //Physical Runway Input
        inputForm.add(runwaySelect, 1,1);

        //Obstacle
        Label obstacleLabel = new Label("Obstacle: ");
        inputForm.add(obstacleLabel, 0,2);

        inputForm.add(obstacleSelect,1,2);

        //Obstacle Info
        inputForm.add(createObstacleInfo(), 0,3,2,1);

        //Lower Threshold Info
        lowerThreshold.getStyleClass().add("thr");
        inputForm.add(lowerThreshold,0,4);

        Label distanceLowerThresholdLabel = new Label("Distance from THR:");
        inputForm.add(distanceLowerThresholdLabel,0,5);
        inputForm.add(addMetre(distanceLowerThreshold),1,5);

        Label sectionLowerThresholdLabel = new Label("Section to use:");
        inputForm.add(sectionLowerThresholdLabel,0,6);
        inputForm.add(sectionLowerThreshold,1,6);

        //Upper Threshold Info
        upperThreshold.getStyleClass().add("thr");
        inputForm.add(upperThreshold,0,7);

        Label distanceUpperThresholdLabel = new Label("Distance from THR:");
        inputForm.add(distanceUpperThresholdLabel,0,8);
        inputForm.add(addMetre(distanceUpperThreshold),1,8);

        Label sectionUpperThresholdLabel = new Label("Section to use:");
        inputForm.add(sectionUpperThresholdLabel,0,9);
        inputForm.add(sectionUpperThreshold,1,9);

        //Calculate Button
        calculateButton.setMaxWidth(Double.MAX_VALUE);
        inputForm.add(calculateButton, 0, 10,2,1);

        //Constraints
        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints(150);
        inputForm.getColumnConstraints().addAll(col1,col2);

        return new Group(inputForm);
    }

    public Node createObstacleInfo() {
        GridPane obstacleInfo = new GridPane();
        obstacleInfo.getStyleClass().add("obstacleInfo");
        obstacleInfo.setAlignment(Pos.CENTER_RIGHT);

        //Height
        Label obstacleHeightLabel = new Label("Height:");
        obstacleInfo.add(obstacleHeightLabel, 0,0);

        obstacleInfo.add(addMetre(obstacleHeight), 1,0);

        //Width
        Label obstacleWidthLabel = new Label("Width:");
        obstacleInfo.add(obstacleWidthLabel, 0,1);

        obstacleInfo.add(addMetre(obstacleWidth), 1,1);

        //Length
        Label obstacleLengthLabel = new Label("Length:");
        obstacleInfo.add(obstacleLengthLabel, 0,2);

        obstacleInfo.add(addMetre(obstacleLength), 1,2);

        return obstacleInfo;
    }

    public Node addMetre(Node input) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        Text metre = new Text("m");
        metre.getStyleClass().add("metre");

        hbox.getChildren().addAll(input, metre);
        return hbox;
    }


}
