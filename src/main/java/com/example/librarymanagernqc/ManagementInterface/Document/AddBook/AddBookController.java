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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private TableColumn<Book, String> idColumn;
    @FXML
    private TableColumn<Book, String> bookTitleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, Void> optionColumn;
    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private void initialize() {
        searchProgressIndicator.setVisible(false);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));

        optionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Book, Void> call(TableColumn<Book, Void> param) {
                return new TableCell<>() {
                    private final JFXButton addButton = new JFXButton();
                    {
                        //create add Image
                        ImageView addImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/add.png")))); // Đường dẫn tới ảnh
                        addImage.setFitWidth(20); // Đặt kích thước cho ảnh
                        addImage.setFitHeight(20);

                        //set addButton
                        addButton.setPadding(Insets.EMPTY);
                        addButton.setRipplerFill(Color.WHITE);
                        addButton.setCursor(Cursor.HAND);
                        addButton.setGraphic(addImage);
                        addButton.setOnAction(event -> {
                            //lấy ô hiện tại đang chọn
                            Book book = getTableView().getItems().get(getIndex());
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
                                        if(BookDatabaseController.isBookExists(newBook.getId())) {
                                            System.out.println("Book with ID " + newBook.getId() + " already exists.");
                                        }
                                        else{
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
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(addButton);
                        }

                        // Căn giữa ảnh trong ô
                        setStyle("-fx-alignment: CENTER;");
                    }
                };
            }
        });
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
                booksTableView.getItems().clear();

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
                        booksTableView.getItems().add(book);
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
