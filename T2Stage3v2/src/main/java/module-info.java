module com.example.t2stage3v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.t2stage3v2 to javafx.fxml;
    exports com.example.t2stage3v2;
}