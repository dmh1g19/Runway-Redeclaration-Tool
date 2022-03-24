package uk.ac.soton.comp2211.views;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import uk.ac.soton.comp2211.airport.*;

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

    private Button topDownView = new Button("Top Down View");
    private Button sideOnView = new Button("Side On View");
    private ComboBox<Pair<Runway, State>> runwayToShow = new ComboBox<>();

    private TableView<Runway> originalRunways = new TableView();
    private TableView<RedeclaredRunway> redeclaredRunways = new TableView();

    private TextFlow breakdown = new TextFlow();

    //GETTERS

    public TextFlow getBreakdown() {
        return breakdown;
    }

    public TableView<RedeclaredRunway> getRedeclaredRunways() {
        return redeclaredRunways;
    }

    public TableView<Runway> getOriginalRunways() {
        return originalRunways;
    }

    public ComboBox<PhysicalRunway> getRunwaySelect() {
        return runwaySelect;
    }

    public ComboBox<Obstacle> getObstacleSelect() {
        return obstacleSelect;
    }

    public TextField getObstacleHeight() {
        return obstacleHeight;
    }

    public Button getTopDownView() {
        return topDownView;
    }

    public Button getSideOnView() {
        return sideOnView;
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

    public ComboBox<Pair<Runway, State>> getRunwayToShow() {
        return runwayToShow;
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
        layout.setRight(createCalcInfoTable());


        return layout;
    }

    public Node createCalcInfoTable() {
        //Original Runways
        originalRunways.setEditable(false);
        originalRunways.setPrefWidth(300);
        originalRunways.setPrefHeight(75);
        originalRunways.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Runway, String> name = new TableColumn<>("THR");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setSortable(false);

        TableColumn<Runway,String> TORA = new TableColumn<>("TORA");
        TORA.setCellValueFactory(new PropertyValueFactory<>("TORA"));
        TORA.setSortable(false);

        TableColumn<Runway,String> TODA = new TableColumn<>("TODA");
        TODA.setCellValueFactory(new PropertyValueFactory<>("TODA"));
        TODA.setSortable(false);

        TableColumn<Runway,String> ASDA = new TableColumn<>("ASDA");
        ASDA.setCellValueFactory(new PropertyValueFactory<>("ASDA"));
        ASDA.setSortable(false);

        TableColumn<Runway,String> LDA = new TableColumn<>("LDA");
        LDA.setCellValueFactory(new PropertyValueFactory<>("LDA"));
        LDA.setSortable(false);

        originalRunways.getColumns().addAll(name,TORA, TODA, ASDA, LDA);

        //Redeclared Runways
        redeclaredRunways.setEditable(false);
        redeclaredRunways.setPrefWidth(300);
        redeclaredRunways.setPrefHeight(75);
        redeclaredRunways.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<RedeclaredRunway, String> reName = new TableColumn<>("THR");
        reName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getRunway().getName()));
        reName.setSortable(false);

        TableColumn<RedeclaredRunway,String> reTORA = new TableColumn<>("TORA");
        reTORA.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRunway().getTORA())));
        reTORA.setSortable(false);

        TableColumn<RedeclaredRunway,String> reTODA = new TableColumn<>("TODA");
        reTODA.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRunway().getTODA())));
        reTODA.setSortable(false);

        TableColumn<RedeclaredRunway,String> reASDA = new TableColumn<>("ASDA");
        reASDA.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRunway().getASDA())));
        reASDA.setSortable(false);

        TableColumn<RedeclaredRunway,String> reLDA = new TableColumn<>("LDA");
        reLDA.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getRunway().getLDA())));
        reLDA.setSortable(false);

        redeclaredRunways.getColumns().addAll(reName,reTORA,reTODA,reASDA,reLDA);

        //Labels
        Label originalRunwaysLabel = new Label("Original Runways");
        originalRunwaysLabel.getStyleClass().add("valuesTableLabel");
        Label redeclaredRunwaysLabel = new Label("Redeclared Runways");
        redeclaredRunwaysLabel.getStyleClass().add("valuesTableLabel");

        //Breakdown
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(breakdown);
        scrollPane.setPrefWidth(300);
        scrollPane.setPrefHeight(270);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        breakdown.setPrefWidth(300);
        breakdown.setMinHeight(270);
        breakdown.getStyleClass().add("breakdown");
        Label breakdownLabel = new Label("Calculations Breakdown");
        breakdownLabel.getStyleClass().add("valuesTableLabel");

        //Vbox
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getStyleClass().add("calcInfo");
        vbox.getChildren().addAll(originalRunwaysLabel,originalRunways,redeclaredRunwaysLabel,redeclaredRunways, breakdownLabel,scrollPane);

        return vbox;
    }

    public Node createBackButton() {
        BorderPane.setMargin(backButton, new Insets(0,0,8,0));

        return backButton;
    }

    public Node createViewButtons() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(24);

        sideOnView.getStyleClass().add("viewButton");
        topDownView.getStyleClass().add("viewButton");
        runwayToShow.getStyleClass().add("runwayToShow");

        vbox.getChildren().addAll(sideOnView, topDownView, runwayToShow);

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
        ColumnConstraints col2 = new ColumnConstraints(160);
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
