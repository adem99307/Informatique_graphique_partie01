module com.example.infograph {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.infograph to javafx.fxml;
    exports com.example.infograph;
}