package com.example.librarymanagernqc.ManagementInterface.Document.AddBook;

import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.Book.BookJsonHandler;
import com.example.librarymanagernqc.Objects.Book.GoogleBooksAPI;
import com.example.librarymanagernqc.database.Controller.BookDatabaseController;
import com.example.librarymanagernqc.ManagementInterface.Document.BookInformation.BookInformationController;
import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AddBookController {
    @FXML
    private StackPane mainStackPane;
    @FXML
    public JFXButton backButton;
    @FXML
    private TextField idField;
    @FXML
    private TextField bookTitleField;
    @FXML
    private TextField authorField;
    @FXML
    private ProgressIndicator searchProgressIndicator;
    @FXML
    private FlowPane bookFlowPane;

    @FXML
    private void initialize() {
        searchProgressIndicator.setVisible(false);
        bookFlowPane.setHgap(45);
        bookFlowPane.setVgap(20);
    }

    private void addBookToFlowPane(Book book) {
        final JFXButton bookButton = new JFXButton();
        {
            //create add Image
            //set default unknown book image
            ImageView bookImageView = new ImageView(new Image(getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/Document/BookInformation/image/unknown_book.png").toExternalForm()));
            // Đặt kích thước cho ảnh
            bookImageView.setFitWidth(145);
            bookImageView.setFitHeight(214);
            //set book image
            if (book.getThumbnailUrl() != null) {
                // Lắng nghe hoàn tất tải ảnh
                Image bookImage = new Image(book.getThumbnailUrl(), true);
                bookImage.progressProperty().addListener((observable, oldProgress, newProgress) -> {
                    if (newProgress.doubleValue() == 1.0) { //if loading image successfully
                        bookImageView.setImage(bookImage);
                    }
                });
            }

            //set bookButton
            bookButton.setStyle(
                    "-fx-border-color: gray;" +
                    "-fx-cursor: HAND;" +
                    "-fx-padding: 0;"
            );
            bookButton.setRipplerFill(Color.WHITE);
            bookButton.setGraphic(bookImageView);
            // Gắn sự kiện thay đổi khi hower
            bookButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    // Khi hower
                    bookButton.setOpacity(0.7);
                } else {
                    // Khi mất hower
                    bookButton.setOpacity(1);
                }
            });

            //mouse click
            bookButton.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    //load book information
                    Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
                    FXMLLoader bookInfoLoader = new FXMLLoader(getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/Document/BookInformation/book-information.fxml"));
                    try {
                        mainStackPane.getChildren().add(bookInfoLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    BookInformationController bookInfoController = bookInfoLoader.getController();
                    bookInfoController.setBook(book);

                    //cancel button event
                    bookInfoController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                        if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                            mainStackPane.getChildren().removeLast();
                            mainStackPane.getChildren().add(savePane);
                        }
                    });

                    //add button event
                    bookInfoController.addButton.setOnMouseClicked(addMouseEvent -> {
                        if (addMouseEvent.getButton() == MouseButton.PRIMARY) {
                            if (bookInfoController.checkValidBook()) {
                                //lấy thông tin sách và thêm vào database
                                Book newBook = bookInfoController.getBook();
                                // kiểm tra book đã tồn tại chưa
                                if (BookDatabaseController.isBookExists(newBook.getId())) {
                                    System.out.println("Book with ID " + newBook.getId() + " already exists.");
                                } else {
                                    boolean isInserted = BookDatabaseController.insertBook(newBook);
                                    if (isInserted) {
                                        System.out.println("Book added to the database successfully.");
                                        //add book
                                        DocumentController.addBookToList(newBook);
                                    } else {
                                        System.out.println("Failed to add the book to the database.");
                                    }
                                }
                                mainStackPane.getChildren().removeLast();
                                mainStackPane.getChildren().add(savePane);
                            }
                        }
                    });
                }
            });
        }

        //title text
        TextArea bookTitleText = new TextArea(book.getTitle());
        bookTitleText.setEditable(false);
        bookTitleText.setWrapText(true);
        bookTitleText.setPrefSize(145, 20);
        bookTitleText.getStylesheets().add(getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/Document/BookInformation/style.css").toExternalForm());
        bookTitleText.getStyleClass().add("longInfoField");

        VBox bookBox = new VBox(bookButton, bookTitleText);
        //add to flow pane
        bookFlowPane.getChildren().add(bookBox);
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
                bookFlowPane.getChildren().clear();

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
                        addBookToFlowPane(book);
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

}
