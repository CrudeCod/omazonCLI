module com.controller {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.controller to javafx.fxml;
    exports com.controller;
}