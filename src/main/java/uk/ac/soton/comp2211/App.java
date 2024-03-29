package uk.ac.soton.comp2211;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.soton.comp2211.airport.Airport;
import uk.ac.soton.comp2211.airport.PhysicalRunway;
import uk.ac.soton.comp2211.airport.Runway;
import uk.ac.soton.comp2211.controllers.MenuController;
import uk.ac.soton.comp2211.models.AirportModel;
import uk.ac.soton.comp2211.utility.XMLUtil;
import uk.ac.soton.comp2211.views.MenuView;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Scene scene;
    private Stage stage;

    private final int height = 600;
    private final int width = 900;

    @Override
    public void start(Stage stage)  {
        this.stage = stage;


        stage.setMinWidth(width);
        stage.setMinHeight(height);

        stage.setTitle("Runway Re-declaration Tool");

        // INPUT RUNWAYS MANUALLY
        Runway r09L = new Runway("09L",3901,50,089.67,3901,3901,3901,3592,0,309);
        Runway r27R = new Runway("27R", 3901,50, 269.71,3882,3960,3882,3882, 78, 0);
        Runway r09R = new Runway("09R", 3658,50, 089.67,3658,3658,3658,3350,0,308);
        Runway r27L = new Runway("27L", 3658,50, 0269.71,3658,3658,3658,3658,0,0);
        PhysicalRunway r09L27R = new PhysicalRunway(r09L,r27R);
        PhysicalRunway r09R27L = new PhysicalRunway(r09R,r27L);

        PhysicalRunway[] runways = new PhysicalRunway[]{r09L27R,r09R27L};
        Airport heathrow = new Airport("London Heathrow", runways);
        Airport[] airports = new Airport[]{heathrow};



        AirportModel model;
        try {
            model = new AirportModel(XMLUtil.importAirports("airports.xml"));

        } catch (Exception e) {
            model = new AirportModel(airports);
        }

        try {
            model.setPreDefinedObstacles(XMLUtil.importObstacles("obstacles.xml"));
        } catch (Exception e) { }

        MenuView view = new MenuView();
        MenuController controller = new MenuController(view, model);

        Scene scene = new Scene(view.getView(), width, height);

        scene.getStylesheets().add(App.class.getResource("main.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("MediumFont.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }



    public void defaultScene() {
        scene = new Scene(new StackPane(),width,height, Color.BLACK);
        stage.setScene(scene);
    }

    public Scene getScene() {
        return this.scene;
    }


    /**
     * FXML FUNCTIONS
     */
    void setRoot(String fxml) {
        try {
            scene = new Scene((loadFXML(fxml)),width,height);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}