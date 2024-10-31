package com.example.librarymanagernqc.ManagementInterface.Document.BookInformation;

import com.example.librarymanagernqc.Book.Book;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BookInformationController {
    @FXML
    private TextField bookTitle;
    @FXML
    private TextArea bookAuthor;
    @FXML
    private TextField bookQuantity;
    @FXML
    private TextField bookPublisher;
    @FXML
    private TextField bookPublishedDate;
    @FXML
    private TextArea bookDescription;

    @FXML
    private void initialize() {
        bookAuthor.setWrapText(true);
        bookDescription.setWrapText(true);
    }

    public void addBook(Book book) {
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthors());
        bookQuantity.setText(String.valueOf(book.getCount()));
        bookPublisher.setText(book.getPublisher());
        bookPublishedDate.setText(book.getPublishedDate());
        bookDescription.setText(book.getDescription());
    }
}
