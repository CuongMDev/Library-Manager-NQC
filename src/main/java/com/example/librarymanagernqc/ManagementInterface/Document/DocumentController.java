package com.example.librarymanagernqc.ManagementInterface.Document;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.ManagementInterface.Document.AddBook.AddBookController;
import com.example.librarymanagernqc.ManagementInterface.Document.BookInformation.BookInformationController;
import com.example.librarymanagernqc.database.Controller.BookDatabaseController;
import com.example.librarymanagernqc.database.Controller.BorrowedListDatabaseController;
import com.example.librarymanagernqc.database.DAO.BookDAO;
import com.jfoenix.controls.JFXButton;
import java.util.Collections;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
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
    @FXML
    private ScrollPane recentScrollPane;
    @FXML
    private FlowPane recentFlowPane;

    private static List<Book> recentList = new ArrayList<>();

    public static final int LIMIT_RECENT_BOOK = 20;

    /**
     * all books list
     */
    private static List<Book> booksList = new LinkedList<>();

    public static void addBookToList(Book book) {
        booksList.add(book);
    }

    public static void setAllRecentList(List<Book> newRecentList) {
        recentList = newRecentList;
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
        addRecentListToTable();
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

    public static void addBookToRecentList(Book book) {
        recentList.remove(book);
        recentList.addFirst(book);
    }

    private void addBookToRecentListTable(Book book) {
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
        recentFlowPane.getChildren().add(bookBox);
    }

    private void addRecentListToTable() {
        recentFlowPane.getChildren().clear();
        int count = Math.min(recentList.size(), LIMIT_RECENT_BOOK);
        for (int i = 0; i < count; i++) {
            addBookToRecentListTable(recentList.get(i));
        }
    }

    private void addBooksListToTable(List<Book> booksList) {
        booksTableView.getItems().clear();
        for (Book book : booksList) {
            booksTableView.getItems().add(book);
        }
    }

    private void initRecentScrollPane() {
        // Tính toán tỷ lệ cuộn ổn định
        double scrollSpeed = 0.005;  // Tốc độ cuộn cố định

        // Tự động cuộn
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(20), e -> {
                    // Tính toán tỷ lệ chiều cao của VBox và ScrollPane
                    double contentHeight = recentFlowPane.getHeight();
                    double viewportHeight = recentScrollPane.getViewportBounds().getHeight();

                    // Tính toán tỷ lệ cuộn phù hợp dựa trên chiều cao
                    double ratio = contentHeight / viewportHeight;

                    // Điều chỉnh tốc độ cuộn dựa trên tỷ lệ này
                    double adjustedScrollSpeed = scrollSpeed / ratio;  // Điều chỉnh tốc độ

                    double currentValue = recentScrollPane.getVvalue();
                    recentScrollPane.setVvalue(currentValue + adjustedScrollSpeed); // Tăng giá trị cuộn dọc
                    if (recentScrollPane.getVvalue() >= 1.0) {
                        recentScrollPane.setVvalue(0.0); // Quay lại đầu nếu cuộn xong
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Thiết lập hành động khi di chuột vào
        recentScrollPane.setOnMouseEntered(event -> {
            timeline.stop();
        });

        // Thiết lập hành động khi chuột rời đi
        recentScrollPane.setOnMouseExited(event -> {
            timeline.play();
        });
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

        initRecentScrollPane();
        recentFlowPane.setVgap(10);
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
