package com.example.librarymanagernqc.ManagementInterface.Document.BookInformation;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.Utils;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookInformationController {
    public enum Type {
        ADD,
        EDIT
    }

    @FXML
    private ImageView bookImage;
    @FXML
    private ImageView bookQrImage;

    private Book book;
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

    public boolean checkValidBook() {
        boolean valid = true;

        if (bookQuantity.getText() == null || Integer.parseInt(bookQuantity.getText()) == 0) {
            if (!bookQuantity.getStyleClass().contains("invalid")) {
                bookQuantity.getStyleClass().add("invalid");
            }
            valid = false;
        } else {
            bookQuantity.getStyleClass().remove("invalid");
        }

        return valid;
    }

    public Book getBook() {
        book.setQuantity(Integer.parseInt(bookQuantity.getText()));
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthors());
        bookQuantity.setText(String.valueOf(book.getQuantity()));
        bookPublisher.setText(book.getPublisher());
        bookPublishedDate.setText(book.getPublishedDate());
        bookDescription.setText(book.getDescription());
        if (book.getThumbnailUrl() != null) {
            bookImage.setImage(new Image(book.getThumbnailUrl()));
        }
        try {
            bookQrImage.setImage(Utils.generateQRCode(book.getInfoLink()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
