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
        if(model.isCustomColours()){
            view.getView().setStyle(model.getCustomColours());
        }
    }

    public void initialise(){
        view.getFontsBox().getSelectionModel().select(model.getFontSize());
        view.getBackButton().setOnMouseClicked(mouseEvent -> loadMenu());

        view.getFontsBox().getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
           updateFont(oldValue, newValue);
        });


        view.getCustomColour().setOnAction(e -> {
            Color c = view.getAccentPicker().getValue();
            Color c2 = view.getBackPicker().getValue();

            if(!model.isCustomColours()){
                view.getView().getScene().getStylesheets().remove(App.class.getResource("main.css").toExternalForm());
                view.getView().getScene().getStylesheets().add(App.class.getResource("choseColour.css").toExternalForm());
            }
            model.setCustomColours("-fx-accentColour:"+toHexString(c)+"; -fx-back:"+toHexString(c2));
            view.getView().setStyle(model.getCustomColours());
            System.out.println("New accent Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
            System.out.println("New background Color's RGB = "+c2.getRed()+" "+c2.getGreen()+" "+c2.getBlue());
            model.setHasCustomColours(true);
            view.getColourBlindOptionsBox().setValue("");
        });

        view.getBlindColour().setOnAction(actionEvent -> {
            Color c = new Color(0,0,0,0);
            Color c2 = new Color(0,0,0,0);
            if(!model.isCustomColours()){
                view.getView().getScene().getStylesheets().remove(App.class.getResource("main.css").toExternalForm());
                view.getView().getScene().getStylesheets().add(App.class.getResource("choseColour.css").toExternalForm());
            }


            view.getColourBlindOptionsBox().getValue();
            switch (view.getColourBlindOptionsBox().getValue()) {
                case "Protanopia" -> {
                    c = Color.YELLOW;
                    c2 = Color.rgb(64,40,11);
                }
                case "Deuteranopia" -> {
                    c =Color.rgb(220,130,253);;
                    c2 = Color.rgb(90,90,90);;
                }
                case "Tritanopia" -> {
                    c = Color.AQUA;
                    c2 = Color.PURPLE;
                }
                case "High Contrast" -> {
                    c = Color.LIME;
                    c2 = Color.BLACK;
                }

                case "Black and White" -> {
                    c = Color.WHITE;
                    c2 = Color.BLACK;
                }
            }
            view.getAccentPicker().setValue(Color.WHITE);
            view.getBackPicker().setValue(Color.WHITE);

            model.setCustomColours("-fx-accentColour:"+toHexString(c)+"; -fx-back:"+toHexString(c2));
            view.getView().setStyle(model.getCustomColours());
            System.out.println("New accent Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
            System.out.println("New background Color's RGB = "+c2.getRed()+" "+c2.getGreen()+" "+c2.getBlue());
            model.setHasCustomColours(true);
        } );

        view.getResetButton().setOnAction(actionEvent -> {
            resetColours();
            view.getColourBlindOptionsBox().setValue("");
            view.getAccentPicker().setValue(Color.WHITE);
            view.getBackPicker().setValue(Color.WHITE);

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
    private  void resetColours(){
        view.getView().getScene().getStylesheets().remove(App.class.getResource("choseColour.css").toExternalForm());
        view.getView().getScene().getStylesheets().add(App.class.getResource("main.css").toExternalForm());
        model.setHasCustomColours(false);
    }


}
