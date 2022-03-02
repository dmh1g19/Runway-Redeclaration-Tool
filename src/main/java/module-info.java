module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens uk.ac.soton.comp2211 to javafx.fxml;
    exports uk.ac.soton.comp2211;
}
