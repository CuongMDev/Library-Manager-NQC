package com.example.librarymanagernqc.ManagementInterface.Document;

import com.example.librarymanagernqc.Book.Book;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class DocumentController {
    @FXML
    TableColumn<Book, String> idColumn;
    @FXML
    TableColumn<Book, String> bookTitleColumn;
    @FXML
    TableColumn<Book, String> authorColumn;
    @FXML
    JFXListView<Book> bookTableView;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));


    }
}
