package uk.ac.soton.comp2211.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EditAirportsView extends BaseView {

    //View Nodes
    private Button backButton = new Button("Back");
    private VBox listVBox = new VBox();
    private VBox editAirportsVBox = new VBox();
    private VBox editRunwaysVBox = new VBox();
    private Button saveAirport = new Button("Save");
    private TextField nameInput = new TextField();
    private Button saveToFile = new Button("Save Changes To Storage");
    private VBox airportList = new VBox();

    //Getters
    public Button getBackButton() {
        return backButton;
    }

    public VBox getListVBox() {
        return listVBox;
    }

    public VBox getEditAirportsVBox() {
        return editAirportsVBox;
    }

    public VBox getEditRunwaysVBox() {
        return editRunwaysVBox;
    }

    public Button getSaveAirport() {
        return saveAirport;
    }

    public TextField getNameInput() {
        return nameInput;
    }

    public Button getSaveToFile() {
        return saveToFile;
    }

    public VBox getAirportList() {
        return airportList;
    }

    public EditAirportsView() {
        view = createView();
    }

    @Override
    public Parent createView() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(8));

        layout.setTop(createTopBar());
        layout.setRight(createListAirports());
        layout.setLeft(createEditAirport());

        return layout;
    }

    public Node createEditAirport() {
        ScrollPane scrollPane = new ScrollPane(editAirportsVBox);
        scrollPane.setPrefWidth(400);
        scrollPane.getStyleClass().add("inputs");

        editAirportsVBox.setMaxWidth(350);
        editAirportsVBox.setPrefWidth(350);
        editAirportsVBox.setSpacing(8);
        Label title = new Label("Add Airport");
        title.getStyleClass().add("settingsTitle");

        editAirportsVBox.getChildren().add(title);
        editAirportsVBox.getChildren().add(new Separator());

        //Airport Name
        HBox airportName = new HBox();
        Label nameLabel = new Label("Name: ");
        nameLabel.setStyle("-fx-alignment: CENTER_LEFT;");

        //Runways
        Label runwayLabel = new Label("Runways");
        runwayLabel.getStyleClass().add("headerCalc");

        editRunwaysVBox.setSpacing(4);
        editRunwaysVBox.getStyleClass().add("runwayInfo");

        airportName.getChildren().addAll(nameLabel, nameInput);
        editAirportsVBox.getChildren().addAll(airportName, runwayLabel, editRunwaysVBox, saveAirport);

        return scrollPane;
    }

    public Node createListAirports() {
        listVBox.setMaxWidth(370);
        listVBox.setPrefWidth(370);
        listVBox.setSpacing(8);
        Label title = new Label("Airports");
        title.getStyleClass().add("settingsTitle");

        listVBox.getChildren().add(title);
        listVBox.getChildren().add(new Separator());
        listVBox.getChildren().add(airportList);
        airportList.setSpacing(4);

        ScrollPane scrollPane = new ScrollPane(listVBox);
        scrollPane.setPrefWidth(400);
        scrollPane.getStyleClass().add("inputs");
        return scrollPane;
    }

    public Node createTopBar() {
        HBox topHBox = new HBox();

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        BorderPane.setMargin(topHBox, new Insets(0,0,4,0));

        topHBox.getChildren().addAll(backButton, region, saveToFile);

        return topHBox;
    }
}
