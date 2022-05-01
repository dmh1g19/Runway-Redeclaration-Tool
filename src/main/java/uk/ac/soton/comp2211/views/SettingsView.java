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
    CheckBox contrastBox;


    ColorPicker accentPicker = new ColorPicker();
    ColorPicker backPicker = new ColorPicker();
    Button customColour = new Button("save colour");


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
        Label fontLabel = new Label("Font Size:");
        menu.add(fontLabel, 0, 1);
        ArrayList<String> fonts = new ArrayList<>();
        fonts.add("Small");
        fonts.add("Medium");
        fonts.add("Large");
        fontsBox = new ComboBox<String>(FXCollections.observableList(fonts.stream().toList()));
        menu.add(fontsBox,1,1);


        Label contrast = new Label("Contrast:");
        menu.add(contrast, 0, 2);
        contrastBox = new CheckBox();
        menu.add(contrastBox, 1, 2);





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

        menu.add(colourBox,0,3);

        menu.setAlignment(Pos.TOP_CENTER);
        menu.setMaxWidth(500);
        menu.setMaxHeight(500);

     ;


        return  menu;
    }

    public Button getBackButton() {
        return backButton;
    }

    public ComboBox<String> getFontsBox() {
        return fontsBox;
    }

    public CheckBox getContrastBox() {
        return contrastBox;
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


}
