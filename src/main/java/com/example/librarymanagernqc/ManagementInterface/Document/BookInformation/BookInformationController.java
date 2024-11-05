package com.example.librarymanagernqc.ManagementInterface.Document.BookInformation;

import com.example.librarymanagernqc.Book.Book;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.Utils;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class BookInformationController {
    public enum Type {
        ADD,
        EDIT
    }

    private String bookId;
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
    public JFXButton cancelButton;
    @FXML
    public JFXButton addButton;

    @FXML
    private void initialize() {
        bookTitle.setEditable(false);
        bookAuthor.setEditable(false);
        bookPublisher.setEditable(false);
        bookPublishedDate.setEditable(false);
        bookDescription.setEditable(false);

        bookAuthor.setWrapText(true);
        bookDescription.setWrapText(true);

        //chỉ cho phép nhập số
        bookQuantity.setTextFormatter(new TextFormatter<>(Utils.numberFilter));
    }

    public void setType(Type type) {
        if (type == Type.EDIT) {
            addButton.setText("Save");
        }
    }

    private boolean checkValidBook() {
        if (bookQuantity.getText() == null || Integer.parseInt(bookQuantity.getText()) == 0) {
            return false;
        }

        return true;
    }

    public boolean checkValidAndHandleBook() {
        if (checkValidBook()) {
            DocumentController.addBookToList(getBook());
            return true;
        }

        if (bookQuantity.getText() == null || Integer.parseInt(bookQuantity.getText()) == 0) {
            if (!bookQuantity.getStyleClass().contains("invalid")) {
                bookQuantity.getStyleClass().add("invalid");
            }
        } else {
            bookQuantity.getStyleClass().remove("invalid");
        }
        return false;
    }

    public Book getBook() {
        return new Book(bookId, bookTitle.getText(), bookAuthor.getText(),
                bookPublisher.getText(), bookPublishedDate.getText(), bookDescription.getText(),
                Integer.parseInt(bookQuantity.getText()));
    }

    public void addBook(Book book) {
        bookId = book.getId();
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthors());
        bookQuantity.setText(String.valueOf(book.getQuantity()));
        bookPublisher.setText(book.getPublisher());
        bookPublishedDate.setText(book.getPublishedDate());
        bookDescription.setText(book.getDescription());
    }
}
