package uk.ac.soton.comp2211.controllers;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import uk.ac.soton.comp2211.App;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.utility.XMLUtil;
import uk.ac.soton.comp2211.views.*;

import java.io.File;
import java.io.IOException;

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
        if (!(model.selectedAirportProperty().get() == null)) view.getAirportIndicator().setText("Airport: " + model.selectedAirportProperty().get().getName());
        else view.getAirportIndicator().setText("Airport: None selected");

        view.getFontsBox().getSelectionModel().select(model.getFontSize());
        view.getBackButton().setOnMouseClicked(mouseEvent -> loadMenu());

        view.getFontsBox().getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
           updateFont(oldValue, newValue);
        });


        view.getCustomColour().setOnAction(e -> {
            Color c = view.getAccentPicker().getValue();
            Color c2 = view.getBackPicker().getValue();

            if(!model.isCustomColours()){
                //.getView().getScene().getStylesheets().remove(App.class.getResource("main.css").toExternalForm());
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
            if (view.getColourBlindOptionsBox().getValue()==null)
                return;
            Color c = new Color(0,0,0,0);
            Color c2 = new Color(0,0,0,0);
            if(!model.isCustomColours()){
                view.getView().getScene().getStylesheets().remove(App.class.getResource("main.css").toExternalForm());
                view.getView().getScene().getStylesheets().add(App.class.getResource("choseColour.css").toExternalForm());
            }



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
                default -> {
                    return;
                }

            }

            if(!model.isCustomColours()){
                //view.getView().getScene().getStylesheets().remove(App.class.getResource("main.css").toExternalForm());
                view.getView().getScene().getStylesheets().add(App.class.getResource("choseColour.css").toExternalForm());
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

        // EDIT AIRPORTS BUTTON
        view.getEditAirportsButton().setOnAction(e -> loadAirportMenu());

        // EDIT OBSTACLES BUTTON
        view.getEditObstaclesButton().setOnAction(e -> loadObstacleMenu());

        // IMPORT AIRPORTS
        view.getImportAirportsButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
            fileChooser.setTitle("Select Airport(s) XML File");
            File file = fileChooser.showOpenDialog(view.getView().getScene().getWindow());
            if (file == null) return;
            try {
                model.airportListProperty().addAll(XMLUtil.importAirports(file));
                Alert importSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                importSuccess.setTitle("Success!");
                importSuccess.setContentText("Airport(s) were imported successfully");
                importSuccess.show();
                model.addAction("Airport(s) successfully imported from: '" + file.getName() + "'");
            } catch (Exception ex) {
                Alert importFail = new Alert(Alert.AlertType.ERROR);
                importFail.setTitle("Error");
                importFail.setContentText("Error importing airport(s) from XML file");
                importFail.show();
                model.addAction("Error: Airport(s) could not be imported from: '" + file.getName() + "'");
            }
        });

        // IMPORT OBSTACLES
        view.getImportObstaclesButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
            fileChooser.setTitle("Select Obstacle(s) XML File");
            File file = fileChooser.showOpenDialog(view.getView().getScene().getWindow());
            if (file == null) return;
            try {
                model.preDefinedObstaclesProperty().addAll(XMLUtil.importObstacles(file));
                Alert importSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                importSuccess.setTitle("Success!");
                importSuccess.setContentText("Obstacle(s) were imported successfully");
                importSuccess.show();
                model.addAction("Obstacle(s) successfully imported from: '" + file.getName() + "'");
            } catch (Exception ex) {
                Alert importFail = new Alert(Alert.AlertType.ERROR);
                importFail.setTitle("Error");
                importFail.setContentText("Error importing obstacle(s) from XML file");
                importFail.show();
                model.addAction("Error: Obstacle(s) could not be imported from: '" + file.getName() + "'");
            }
        });

        // EXPORT AIRPORTS
        view.getExportAirportsButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
            fileChooser.setTitle("Create file to save airports to");
            File file = fileChooser.showSaveDialog(view.getView().getScene().getWindow());
            if (file == null) return;
            try {
                XMLUtil.exportAirports(file, model.airportListProperty().get());
                Alert importSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                importSuccess.setTitle("Success!");
                importSuccess.setContentText("Airports were successfully exported");
                importSuccess.show();
                model.addAction("Airport(s) successfully exported to: '" + file.getName() + "'");
            } catch (IOException ex) {
                Alert importFail = new Alert(Alert.AlertType.ERROR);
                importFail.setTitle("Error");
                importFail.setContentText("Error exporting Airports");
                importFail.show();
                model.addAction("Error: Airport(s) could not be exported to: '" + file.getName() + "'");
            }
        });

        // EXPORT OBSTACLES
        view.getExportObstaclesButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
            fileChooser.setTitle("Create file to save obstacles to");
            File file = fileChooser.showSaveDialog(view.getView().getScene().getWindow());
            if (file == null) return;
            try {
                XMLUtil.exportObstacles(file, model.preDefinedObstaclesProperty().get());
                Alert importSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                importSuccess.setTitle("Success!");
                importSuccess.setContentText("Obstacles were successfully exported");
                importSuccess.show();
                model.addAction("Obstacle(s) successfully exported to: '" + file.getName() + "'");
            } catch (IOException ex) {
                Alert importFail = new Alert(Alert.AlertType.ERROR);
                importFail.setTitle("Error");
                importFail.setContentText("Error exporting Obstacles");
                importFail.show();
                model.addAction("Error: Obstacle(s) could not be exported to: '" + file.getName() + "'");
            }
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
        view.getView().getScene().getStylesheets().clear();
        view.getView().getScene().getStylesheets().add(App.class.getResource("main.css").toExternalForm());
        String fontSize;
        if(model.getFontSize()==0){
            fontSize="Small";
        }else if(model.getFontSize()==2){
            fontSize="Large";
        }else{
            fontSize="Medium";
        }
        view.getView().getScene().getStylesheets().add(App.class.getResource(fontSize+"Font.css").toExternalForm());
        model.setHasCustomColours(false);
    }

    public void loadObstacleMenu() {
        ObstacleCreationView obstaclesView = new ObstacleCreationView();
        ObstacleCreationController obstacleCreationController = new ObstacleCreationController(obstaclesView, model);
        view.getView().getScene().setRoot(obstaclesView.getView());
    }

    public void loadAirportMenu() {
        EditAirportsView editAirportsView = new EditAirportsView();
        EditAirportsController editAirportsController = new EditAirportsController(editAirportsView, model);
        view.getView().getScene().setRoot(editAirportsView.getView());
    }

}
