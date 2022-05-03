package uk.ac.soton.comp2211.views;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ObstacleCreationView extends BaseView {

    private Button backButton = new Button("Back");
    private TextField obstacleName = new TextField();
    private TextField obstacleWidth = new TextField();
    private TextField obstacleHeight = new TextField();
    private TextField obstacleLength = new TextField();
    private Button addObstacle = new Button("Add Obstacle");
    private HBox hBox = new HBox();

    private Button writeObstacles = new Button("Save Changes");

    private VBox obsForm = new VBox();



    public ObstacleCreationView(){
        view=createView();
    }

    @Override
    public Parent createView() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(8));


        hBox.getChildren().add(createInputForm());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("inputs");

        scrollPane.setContent(createObsForm());
        hBox.getChildren().add(scrollPane);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        //adding form

        VBox vBox = new VBox();

        vBox.getChildren().add(hBox);
        vBox.getChildren().add(writeObstacles);
        layout.setCenter(vBox);

        //adding back button

        layout.setLeft(backButton);

        return layout;

    }


    private Node createInputForm() {
        GridPane inputForm = new GridPane();
        inputForm.getStyleClass().add("inputs");

        //Title
        Label headerLabel = new Label("Add Obstacles");
        headerLabel.getStyleClass().add("headerCalc");
        VBox vbox = new VBox(headerLabel, new Separator(Orientation.HORIZONTAL));
        inputForm.add(vbox,0,0,2,1);

        //Name
        Label nameLabel = new Label("Name:");
        inputForm.add(nameLabel, 0, 1);
        inputForm.add(obstacleName,1,1);

        //Width
        Label widthLabel = new Label("Width:");
        inputForm.add(widthLabel, 0, 2);
        inputForm.add(obstacleWidth,1,2);


        //Height
        Label heightLabel = new Label("Height:");
        inputForm.add(heightLabel, 0, 3);
        inputForm.add(obstacleHeight,1,3);


        //length
        Label lengthLabel = new Label("Length:");
        inputForm.add(lengthLabel, 0, 4);
        inputForm.add(obstacleLength,1,4);


        //addObstacle
        inputForm.add(addObstacle,0,5);


        return new Group(inputForm);
    }


    private Node createObsForm() {
        obsForm.setSpacing(8);

        //Title
        Label headerLabel = new Label("Remove Obstacles");
        headerLabel.getStyleClass().add("headerCalc");
        VBox vbox = new VBox(headerLabel, new Separator(Orientation.HORIZONTAL));
        obsForm.getChildren().add(vbox);

        return new Group(obsForm);
    }




    public Button getBackButton() {
        return backButton;
    }

    public TextField getObstacleName() {
        return obstacleName;
    }

    public TextField getObstacleWidth() {
        return obstacleWidth;
    }

    public TextField getObstacleHeight() {
        return obstacleHeight;
    }

    public TextField getObstacleLength() {
        return obstacleLength;
    }

    public Button getAddObstacle() {
        return addObstacle;
    }

    public Button getWriteObstacles() {
        return writeObstacles;
    }
    public VBox getObsForm() {
        return obsForm;
    }



}
