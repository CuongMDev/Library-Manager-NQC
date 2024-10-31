package com.example.librarymanagernqc.ManagementInterface.Document;

import com.example.librarymanagernqc.Book.Book;
import com.example.librarymanagernqc.ManagementInterface.Document.AddBook.AddBookController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DocumentController {
    @FXML
    private TableColumn<Book, String> idColumn;
    @FXML
    private TableColumn<Book, String> bookTitleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private StackPane mainStackPane;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
    }

    @FXML
    private void onAddMouseClicked() throws IOException {
        Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
        FXMLLoader addBookLoader = new FXMLLoader(getClass().getResource("AddBook/add-book.fxml"));
        mainStackPane.getChildren().add(addBookLoader.load());

        AddBookController addBookController = addBookLoader.getController();
        addBookController.backButton.setOnMouseClicked(event -> {
            mainStackPane.getChildren().removeLast();
            mainStackPane.getChildren().add(savePane);
        });
    }
}
