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
    requires javafx.swing;

    opens com.example.librarymanagernqc to javafx.fxml;
    exports com.example.librarymanagernqc;
    exports com.example.librarymanagernqc.ManagementInterface;
    opens com.example.librarymanagernqc.ManagementInterface to javafx.fxml;
    exports com.example.librarymanagernqc.ManagementInterface.Document;
    opens com.example.librarymanagernqc.ManagementInterface.Document to javafx.fxml;
    exports com.example.librarymanagernqc.ManagementInterface.Document.AddBook;
    opens com.example.librarymanagernqc.ManagementInterface.Document.AddBook to javafx.fxml;
    exports com.example.librarymanagernqc.ManagementInterface.Document.BookInformation;
    opens com.example.librarymanagernqc.ManagementInterface.Document.BookInformation to javafx.fxml;
    exports com.example.librarymanagernqc.ManagementInterface.User;
    opens com.example.librarymanagernqc.ManagementInterface.User to javafx.fxml;
    exports com.example.librarymanagernqc.ManagementInterface.User.AddUser;
    opens com.example.librarymanagernqc.ManagementInterface.User.AddUser to javafx.fxml;
    exports com.example.librarymanagernqc.User;
    exports com.example.librarymanagernqc.Book;
    exports com.example.librarymanagernqc.ManagementInterface.BorrowedList;
    opens com.example.librarymanagernqc.ManagementInterface.BorrowedList to javafx.fxml;
}