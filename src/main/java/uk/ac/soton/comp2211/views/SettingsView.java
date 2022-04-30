package uk.ac.soton.comp2211.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uk.ac.soton.comp2211.scene.BaseScene;
import uk.ac.soton.comp2211.scene.SettingsScene;

import java.util.ArrayList;

public class SettingsView extends BaseView {
    Button backButton = new Button("Back");
    ComboBox<String> fontsBox;
    CheckBox contrastBox;



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
        Label fontLabel = new Label("Name:");
        menu.add(fontLabel, 0, 1);
        ArrayList<String> fonts = new ArrayList<>();
        fonts.add("Small");
        fonts.add("Medium");
        fonts.add("Large");
        fontsBox = new ComboBox<String>(FXCollections.observableList(fonts.stream().toList()));
        menu.add(fontsBox,1,1);


        Label contrast = new Label("contrast:");
        menu.add(contrast, 0, 2);
        contrastBox = new CheckBox();
        menu.add(contrastBox, 1, 2);


        menu.setAlignment(Pos.TOP_CENTER);
        menu.setMaxWidth(300);
        menu.setMaxHeight(500);
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

}
