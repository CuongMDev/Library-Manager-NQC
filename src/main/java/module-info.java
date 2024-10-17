module com.example.librarymanagernqc {
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.jfoenix;
    requires java.net.http;

    opens com.example.librarymanagernqc to javafx.fxml;
    exports com.example.librarymanagernqc;
    exports com.example.librarymanagernqc.ManagementInterface;
    opens com.example.librarymanagernqc.ManagementInterface to javafx.fxml;
}