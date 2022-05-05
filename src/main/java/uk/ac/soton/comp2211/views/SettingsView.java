package uk.ac.soton.comp2211.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsView extends BaseView {
    private Button backButton = new Button("Back");
    private ComboBox<String> fontsBox;

    private ColorPicker accentPicker = new ColorPicker();
    private ColorPicker backPicker = new ColorPicker();
    private Button customColour = new Button("Apply Colour Options");
    private Button blindColour = new Button("Apply Colourblind Options");
    private Button resetColours = new Button("Reset All Colour Options");
    private ComboBox<String> colourBlindOptionsBox;
    private Label airportIndicator = new Label();
    private Button editObstaclesButton = new Button("Edit");
    private Button editAirportsButton = new Button("Edit");
    private Button importAirportsButton = new Button("Import");
    private Button importObstaclesButton = new Button("Import");
    private Button exportAirportsButton = new Button("Export");
    private Button exportObstaclesButton = new Button("Export");

    public SettingsView() { view = createView(); }

    @Override
    public Parent createView() {
        BorderPane layout = new BorderPane();

        layout.setPadding(new Insets(8));

        layout.setTop(createTopBar());
        layout.setCenter(createMenu());

        return layout;
    }

    private Node createTopBar() {
        HBox hbox = new HBox();

        BorderPane.setMargin(hbox, new Insets(0,0,4,0));

        backButton.setAlignment(Pos.TOP_LEFT);
        airportIndicator.setAlignment(Pos.TOP_RIGHT);
        airportIndicator.getStyleClass().add("airportIndicator");

        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        hbox.getChildren().addAll(backButton, spacing,airportIndicator);

        return hbox;
    }

    private Node createMenu(){
        VBox menu = new VBox();
        menu.setSpacing(4);
        //menu.getStyleClass().add("inputs");

        Label headerLabel = new Label("Settings");
        headerLabel.getStyleClass().add("settingsTitle");
        headerLabel.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(headerLabel, new Separator(Orientation.HORIZONTAL));


        // Font Options LABEL
        Label fontOptions = new Label("Font Options");
        fontOptions.getStyleClass().add("settingsHeader");
        menu.getChildren().add(fontOptions);

        // Font Options
        Label fontLabel = new Label("Font Size:");
        ArrayList<String> fonts = new ArrayList<>(Arrays.asList("Small","Medium","Large"));
        fontsBox = new ComboBox<>(FXCollections.observableList(fonts.stream().toList()));

        HBox fontHbox = new HBox();
        fontHbox.setAlignment(Pos.CENTER_LEFT);
        fontHbox.getChildren().addAll(fontLabel, fontsBox);
        menu.getChildren().add(fontHbox);



        //custom colours
        VBox colourBox = new VBox();
        colourBox.setSpacing(4);
        HBox accentBox = new HBox();
        HBox backBox = new HBox();
        Label accentPickerLabel = new Label("Custom Text Colour:");
        Label backPickerLabel = new Label("Custom Background Colour:");

        accentBox.getChildren().add(accentPickerLabel);
        accentBox.getChildren().add(accentPicker);
        backBox.getChildren().add(backPickerLabel);
        backBox.getChildren().add(backPicker);
        colourBox.getChildren().add(accentBox);
        colourBox.getChildren().add(backBox);
        colourBox.getChildren().add(customColour);

        Label customColours = new Label("Colour Options");
        customColours.getStyleClass().add("settingsHeader");

        menu.getChildren().add(customColours);

        menu.getChildren().add(colourBox);

        // Colour Blind Options LABEL
        Label blindColours = new Label("Colour Blindness Options");
        blindColours.getStyleClass().add("settingsHeader");
        menu.getChildren().add(blindColours);

        // Colour Blind Options
        ArrayList<String> blinds = new ArrayList<>();
        blinds.add("Protanopia");
        blinds.add("Deuteranopia");
        blinds.add("Tritanopia");
        blinds.add("High Contrast");
        blinds.add("Black and White");
        colourBlindOptionsBox = new ComboBox<String>(FXCollections.observableList(blinds.stream().toList()));

        Label selectBlindessType = new Label("Select Colourblind Option: ");
        HBox colourBlindHBox = new HBox(selectBlindessType, colourBlindOptionsBox);


        menu.getChildren().add(colourBlindHBox);
        menu.getChildren().add(blindColour);

        VBox.setMargin(resetColours, new Insets(20,0,0,0));
        HBox resetAllHBox = new HBox();
        Region resetSpacing = new Region();
        HBox.setHgrow(resetSpacing, Priority.ALWAYS);
        resetAllHBox.getChildren().addAll(resetSpacing, resetColours);
        menu.getChildren().add(resetAllHBox);

        // Edit Airports
        Label createAirportLabel = new Label("Edit Airports");
        createAirportLabel.getStyleClass().add("settingsHeader");
        menu.getChildren().add(createAirportLabel);

        menu.getChildren().add(editAirportsButton);

        // Create Obstacles Label
        Label createObstacleLabel = new Label("Edit Obstacles");
        createObstacleLabel.getStyleClass().add("settingsHeader");
        menu.getChildren().add(createObstacleLabel);

        // Create Obstacle Button
        menu.getChildren().add(editObstaclesButton);


        // Import and Export XML Label
        Label importexportLabel = new Label("Import and Export Airports/Obstacles");
        importexportLabel.getStyleClass().add("settingsHeader");
        menu.getChildren().add(importexportLabel);

        //Import and Export XML
        Label importAirports = new Label("Import Airport(s): "); // IMPORT AIRPORTS
        HBox importAirportsHBox = new HBox();
        importAirportsHBox.getChildren().addAll(importAirports, importAirportsButton);
        importAirportsHBox.setAlignment(Pos.CENTER_LEFT);
        menu.getChildren().add(importAirportsHBox);

        Label importObstacles = new Label("Import Obstacle(s): "); // IMPORT OBSTACLES
        HBox importObstaclesHBox = new HBox();
        importObstaclesHBox.getChildren().addAll(importObstacles, importObstaclesButton);
        importObstaclesHBox.setAlignment(Pos.CENTER_LEFT);
        menu.getChildren().add(importObstaclesHBox);

        Label exportAirports = new Label("Export Airport(s): "); // EXPORT AIRPORTS
        HBox exportAirportsHBox = new HBox();
        exportAirportsHBox.getChildren().addAll(exportAirports, exportAirportsButton);
        exportAirportsHBox.setAlignment(Pos.CENTER_LEFT);
        menu.getChildren().add(exportAirportsHBox);

        Label exportObstacles = new Label("Export Obstacle(s): "); // EXPORT OBSTACLES
        HBox exportObstaclesHBox = new HBox();
        exportObstaclesHBox.getChildren().addAll(exportObstacles, exportObstaclesButton);
        exportObstaclesHBox.setAlignment(Pos.CENTER_LEFT);
        menu.getChildren().add(exportObstaclesHBox);

        menu.setAlignment(Pos.TOP_LEFT);
        menu.setPrefWidth(450);

        ScrollPane settingsMenuScroll = new ScrollPane(menu);
        settingsMenuScroll.setMaxWidth(500);
        settingsMenuScroll.setMaxHeight(500);

        settingsMenuScroll.getStyleClass().add("inputs");

        return settingsMenuScroll;
    }

    public Button getBackButton() {
        return backButton;
    }

    public ComboBox<String> getFontsBox() {
        return fontsBox;
    }

    public ColorPicker getAccentPicker() {
        return accentPicker;
    }

    public ColorPicker getBackPicker() {
        return backPicker;
    }
    public Button getCustomColour(){
        return customColour;
    }

    public Button getBlindColour(){
        return blindColour;
    }

    public Button getResetButton() {
        return resetColours;
    }

    public ComboBox<String> getColourBlindOptionsBox() {
        return colourBlindOptionsBox;
    }

    public Label getAirportIndicator() {
        return airportIndicator;
    }

    public Button getEditObstaclesButton() {
        return editObstaclesButton;
    }

    public Button getImportAirportsButton() {
        return importAirportsButton;
    }

    public Button getImportObstaclesButton() {
        return importObstaclesButton;
    }

    public Button getExportAirportsButton() {
        return exportAirportsButton;
    }

    public Button getExportObstaclesButton() {
        return exportObstaclesButton;
    }

    public Button getEditAirportsButton() {
        return editAirportsButton;
    }
}
