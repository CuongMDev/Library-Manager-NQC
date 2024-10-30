package com.example.librarymanagernqc.ManagementInterface.Document.AddBook;

import com.example.librarymanagernqc.Book.Book;
import com.example.librarymanagernqc.Book.BookJsonHandler;
import com.example.librarymanagernqc.Book.GoogleBooksAPI;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class AddBookController {
    @FXML
    TextField idField;
    @FXML
    TextField bookTitleField;
    @FXML
    TextField authorField;
    @FXML
    ProgressIndicator searchProgressIndicator;
    @FXML
    TableColumn<Book, String> idColumn;
    @FXML
    TableColumn<Book, String> bookTitleColumn;
    @FXML
    TableColumn<Book, String> authorColumn;
    @FXML
    TableView<Book> bookTableView;

    @FXML
    private void initialize() {
        searchProgressIndicator.setVisible(false);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
    }

    @FXML
    private void onKeyPressed(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if (code == KeyCode.ENTER) {
            String id = idField.getText();
            String title = bookTitleField.getText();
            String author = authorField.getText();

            if (!id.isEmpty() || !title.isEmpty() || !author.isEmpty()) {
                searchProgressIndicator.setVisible(true);
                bookTableView.getItems().clear();

                Task<String> searchTask = null;
                if (!id.isEmpty()) {
                    searchTask = GoogleBooksAPI.searchBookById(id);
                } else {
                    searchTask = GoogleBooksAPI.searchBooks(title, author);
                }

                Task<String> finalSearchTask = searchTask;
                searchTask.setOnSucceeded(event -> {
                    List<Book> books = BookJsonHandler.parseBookTitles(finalSearchTask.getValue());
                    for (Book book : books) {
                        bookTableView.getItems().add(book);
                    }
                    searchProgressIndicator.setVisible(false);
                });

                searchTask.setOnFailed(event -> {
                    System.out.println(finalSearchTask.getException().getMessage());
                    searchProgressIndicator.setVisible(false);
                });

                // Chạy task trong một luồng nền
                new Thread(searchTask).start();
            }
        }
    }

    @FXML
    private void onBackMouseClicked(MouseEvent mouseEvent) {
    }
}
