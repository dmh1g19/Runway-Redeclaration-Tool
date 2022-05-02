package uk.ac.soton.comp2211.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uk.ac.soton.comp2211.scene.BaseScene;
import uk.ac.soton.comp2211.scene.SettingsScene;

import java.util.ArrayList;

public class SettingsView extends BaseView {
    Button backButton = new Button("Back");
    ComboBox<String> fontsBox;

    ColorPicker accentPicker = new ColorPicker();
    ColorPicker backPicker = new ColorPicker();
    Button customColour = new Button("save custom colours");
    Button blindColour = new Button("save colour blindness settings");
    Button resetColours = new Button("reset colours");
    ComboBox<String> colourBlindOptionsBox;

    public SettingsView() { view = createView();}

    @Override
    public Parent createView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(8));
        Label headerLabel = new Label("Settings");
        headerLabel.getStyleClass().add("menuItem");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(headerLabel);
        borderPane.setTop(vBox);
        borderPane.setCenter(createMenu());
        borderPane.setLeft(backButton);
        return  borderPane;


    }


    private Node createMenu(){
        GridPane menu = new GridPane();
        menu.getStyleClass().add("inputs");


        //Font
        Label fontOptions = new Label("Font Options");
        fontOptions.setAlignment(Pos.CENTER);
        fontOptions.getStyleClass().add("settingsHeader");
        menu.add(fontOptions,0,0);

        Label fontLabel = new Label("Font Size:");
        menu.add(fontLabel, 0, 1);
        ArrayList<String> fonts = new ArrayList<>();
        fonts.add("Small");
        fonts.add("Medium");
        fonts.add("Large");
        fontsBox = new ComboBox<String>(FXCollections.observableList(fonts.stream().toList()));
        menu.add(fontsBox,1,1);





        //custom colours
        VBox colourBox = new VBox();
        HBox accentBox = new HBox();
        HBox backBox = new HBox();
        Label accentPickerLabel = new Label("Custom Accent Colour:");
        Label backPickerLabel = new Label("Custom Background Colour:");

        accentBox.getChildren().add(accentPickerLabel);
        accentBox.getChildren().add(accentPicker);
        backBox.getChildren().add(backPickerLabel);
        backBox.getChildren().add(backPicker);
        colourBox.getChildren().add(accentBox);
        colourBox.getChildren().add(backBox);
        colourBox.getChildren().add(customColour);





        Label customColours = new Label("Colour Options");
        customColours.setAlignment(Pos.CENTER);
        customColours.getStyleClass().add("settingsHeader");

        menu.add(customColours,0,3);

        menu.add(colourBox,0,4);


        //colour blind options
        VBox blindBox = new VBox();
        ArrayList<String> blinds = new ArrayList<>();
        blinds.add("Protanopia");
        blinds.add("Deuteranopia");
        blinds.add("Tritanopia");
        blinds.add("High Contrast");
        blinds.add("Black and White");
        colourBlindOptionsBox = new ComboBox<String>(FXCollections.observableList(blinds.stream().toList()));
        blindBox.getChildren().add(colourBlindOptionsBox);
        blindBox.getChildren().add(blindColour);

        Label blindColours = new Label("Colour Blindness Options");
        blindColours.setAlignment(Pos.CENTER);
        blindColours.getStyleClass().add("settingsHeader");
        menu.add(blindColours,0,5);
        menu.add(blindBox,0,6);

        menu.add(resetColours,1,10);

        menu.setAlignment(Pos.TOP_CENTER);
        menu.setMaxWidth(500);
        menu.setMaxHeight(500);




        return  menu;
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
}
