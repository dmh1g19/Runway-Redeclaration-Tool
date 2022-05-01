package uk.ac.soton.comp2211.controllers;

import javafx.scene.paint.Color;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.views.MenuView;
import uk.ac.soton.comp2211.views.SelectionView;
import uk.ac.soton.comp2211.views.SettingsView;

public class SettingsController {

    //Model
    AirportModel model;

    //View
    SettingsView view;

    public SettingsController(SettingsView view, AirportModel model) {
        this.view = view;
        this.model = model;
        initialise();
    }

    public void initialise(){
        view.getFontsBox().getSelectionModel().select(model.getFontSize());
        view.getBackButton().setOnMouseClicked(mouseEvent -> loadMenu());

        view.getFontsBox().getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
           updateFont(oldValue, newValue);
        });
        view.getContrastBox().selectedProperty().set(model.isHighContrast());
        view.getContrastBox().setOnMouseClicked(mouseEvent -> {
           boolean contrast = view.getContrastBox().isSelected();
            model.setHighContrast(contrast);
            view.getView().getScene().getStylesheets().remove(App.class.getResource("choseColour.css").toExternalForm());
            if( contrast){
                view.getView().getScene().getStylesheets().remove(App.class.getResource("main.css").toExternalForm());
                view.getView().getScene().getStylesheets().add(App.class.getResource("highContrast.css").toExternalForm());
            }else{
                view.getView().getScene().getStylesheets().remove(App.class.getResource("highContrast.css").toExternalForm());
                view.getView().getScene().getStylesheets().add(App.class.getResource("main.css").toExternalForm());

            }

        });

        view.getCustomColour().setOnAction(e -> {
            Color c = view.getAccentPicker().getValue();
            Color c2 = view.getBackPicker().getValue();
            view.getView().getScene().getStylesheets().remove(App.class.getResource("main.css").toExternalForm());
            view.getView().getScene().getStylesheets().remove(App.class.getResource("highContrast.css").toExternalForm());
            view.getView().getScene().getStylesheets().add(App.class.getResource("choseColour.css").toExternalForm());
            view.getView().setStyle("-fx-accentColour:"+toHexString(c)+"; -fx-back:"+toHexString(c2));
            System.out.println("New accent Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
            System.out.println("New background Color's RGB = "+c2.getRed()+" "+c2.getGreen()+" "+c2.getBlue());
        });


    }

    public void loadMenu() {
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView, model);
        view.getView().getScene().setRoot(menuView.getView());
    }


    private  void updateFont(String oldFont,String newFont){
        view.getView().getScene().getStylesheets().remove(App.class.getResource(oldFont+"Font.css").toExternalForm());
        view.getView().getScene().getStylesheets().add(App.class.getResource(newFont+"Font.css").toExternalForm());
        switch (newFont) {
            case "Small" -> model.setFontSize(0);
            case "Medium" -> model.setFontSize(1);
            case "Large" -> model.setFontSize(2);
        }
    }


    // Helper method
    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    public String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }


}
