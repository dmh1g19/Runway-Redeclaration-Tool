module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens uk.ac.soton.comp2211 to javafx.fxml;
    exports uk.ac.soton.comp2211;
    exports uk.ac.soton.comp2211.scene;
    opens uk.ac.soton.comp2211.scene to javafx.fxml;
    exports uk.ac.soton.comp2211.airport;
    exports uk.ac.soton.comp2211.utility;

}
