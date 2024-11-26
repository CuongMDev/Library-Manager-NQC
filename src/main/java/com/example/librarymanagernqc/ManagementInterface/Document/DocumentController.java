package com.example.librarymanagernqc.ManagementInterface.Document;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.ManagementInterface.Document.AddBook.AddBookController;
import com.example.librarymanagernqc.ManagementInterface.Document.BookInformation.BookInformationController;
import com.example.librarymanagernqc.database.Controller.BookDatabaseController;
import com.example.librarymanagernqc.database.Controller.BorrowedListDatabaseController;
import com.example.librarymanagernqc.database.DAO.BookDAO;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DocumentController {
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
    private StackPane mainStackPane;
    @FXML
    private TextField searchField;

    /**
     * all books list
     */
    private static List<Book> booksList = new LinkedList<>();

    public static void addBookToList(Book book) {
        booksList.add(book);
    }

    public static void setAllBooksList(List<Book> newBooksList) {
        booksList = newBooksList;
    }

    private void deleteBookFromList(Book book) {

        boolean isDeletedFromDb = BookDatabaseController.deleteBookById(book.getId());  // Xóa khỏi database

        if (isDeletedFromDb) {
            booksList.remove(book);  // Xóa khỏi danh sách sau khi xóa thành công trong database
            updateTable();  // Cập nhật bảng để phản ánh sự thay đổi
        } else {
            System.out.println(BookDatabaseController.getErrorMessage());  // Thông báo nếu xóa không thành công
        }
    }

    public static void changeBookQuantity(Book book, int changeQuantity) {
        book.setQuantity(book.getQuantity() + changeQuantity);
        // cập nhật số lượng sách trng database
        BookDAO.changeQuantityBook(book.getQuantity(), book.getId());
    }

    /**
     * search book by title, limit = 0 mean no limit
     */
    public static List<Book> searchBooksList(String title, int limit) {
        return Book.fuzzySearch(booksList, title, 0, limit);
    }

    /**
     * search book by id
     */
    public static Book searchBookById(String id) {
        for (Book book : booksList) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public void updateTable() {
        if (searchField.getText().isEmpty()) {
            addBooksListToTable(booksList);
        } else {
            addBooksListToTable(searchBooksList(searchField.getText(), 0));
        }
    }

    private void initOptionColumns() {
        optionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Book, Void> call(TableColumn<Book, Void> param) {
                return new TableCell<>() {
                    private final JFXButton editButton = new JFXButton();
                    {
                        //create add Image
                        ImageView editImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/edit.png")))); // Đường dẫn tới ảnh
                        editImage.setFitWidth(20); // Đặt kích thước cho ảnh
                        editImage.setFitHeight(20);

                        //set editButton

                        editButton.setPadding(Insets.EMPTY);
                        editButton.setRipplerFill(Color.WHITE);
                        editButton.setCursor(Cursor.HAND);
                        editButton.setGraphic(editImage);
                        editButton.setOnAction(event -> {
                            //lấy ô hiện tại đang chọn
                            Book currentBook = getTableView().getItems().get(getIndex());
                            //load book information
                            Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
                            FXMLLoader bookInfoLoader = new FXMLLoader(getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/Document/BookInformation/book-information.fxml"));
                            try {
                                mainStackPane.getChildren().add(bookInfoLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            BookInformationController bookInfoController = bookInfoLoader.getController();
                            bookInfoController.setBook(currentBook);
                            bookInfoController.setType(BookInformationController.Type.EDIT);

                            //save button event
                            bookInfoController.addButton.setOnMouseClicked(addMouseEvent -> {
                                if (addMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    if (bookInfoController.checkValidBook()) {
                                        currentBook.setQuantity(bookInfoController.getBook().getQuantity());

                                        boolean isUpdated = BookDatabaseController.updateBook(currentBook);

                                        if(isUpdated) {
                                            System.out.println("Book updated successfully in database");
                                        }
                                        else{
                                            System.out.println("Failed to update book in database");
                                        }

                                        mainStackPane.getChildren().removeLast();
                                        mainStackPane.getChildren().add(savePane);
                                    }
                                }
                            });

                            //cancel button event
                            bookInfoController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    mainStackPane.getChildren().removeLast();
                                    mainStackPane.getChildren().add(savePane);
                                }
                            });

                        });
                    }

                    private final JFXButton deleteButton = new JFXButton();
                    {
                        //create add Image
                        ImageView deleteImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/delete.png")))); // Đường dẫn tới ảnh
                        deleteImage.setFitWidth(20); // Đặt kích thước cho ảnh
                        deleteImage.setFitHeight(20);

                        //set deleteButton

                        deleteButton.setPadding(Insets.EMPTY);
                        deleteButton.setRipplerFill(Color.WHITE);
                        deleteButton.setCursor(Cursor.HAND);
                        deleteButton.setGraphic(deleteImage);
                        deleteButton.setOnAction(event -> {
                            // Lấy sách hiện tại đang được chọn
                            Book book = getTableView().getItems().get(getIndex());

                            // Kiểm tra nếu sách đang được mượn
                            if (BorrowedListDatabaseController.isBookLoanExistBybookId(book.getId())) {
                                // Hiển thị thông báo
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Can't delete a book");
                                alert.setHeaderText(null);
                                alert.setContentText("The book cannot be deleted because it is being borrowed.");
                                alert.showAndWait();
                            } else {
                                // Xóa sách khỏi danh sách và database
                                deleteBookFromList(book);
                            }
                        });

                    }

                    private final HBox buttonsBox = new HBox(10, editButton, deleteButton);
                    {
                        buttonsBox.setAlignment(Pos.CENTER);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(buttonsBox);
                        }

                        // Căn giữa ảnh trong ô
                        setStyle("-fx-alignment: CENTER;");
                    }
                };
            }
        });
    }

    private void addBooksListToTable(List<Book> booksList) {
        booksTableView.getItems().clear();
        for (Book book : booksList) {
            booksTableView.getItems().add(book);
        }
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        initOptionColumns();
        // Lắng nghe thay đổi của text
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTable();
        });
    }

    @FXML
    private void onAddMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
            FXMLLoader addBookLoader = new FXMLLoader(getClass().getResource("AddBook/add-book.fxml"));
            mainStackPane.getChildren().add(addBookLoader.load());

            AddBookController addBookController = addBookLoader.getController();
            addBookController.backButton.setOnMouseClicked(addBookMouseEvent -> {
                if (addBookMouseEvent.getButton() == MouseButton.PRIMARY) {
                    mainStackPane.getChildren().removeLast();
                    mainStackPane.getChildren().add(savePane);
                }

                updateTable();
            });
        }
    }
}
