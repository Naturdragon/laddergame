module com.example.theos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.theos to javafx.fxml;
    exports com.example.theos;
    requires java.desktop;
}
