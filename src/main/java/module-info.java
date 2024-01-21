module com.example.theos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    exports com.example.theos;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.theos to javafx.fxml;
    requires java.desktop;
}
