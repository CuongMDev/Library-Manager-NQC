package com.example.librarymanagernqc.ManagementInterface.ReturnedList;

import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.ManagementInterface.BorrowedList.RecordBookReturn.RecordBookReturnController;
import com.example.librarymanagernqc.TimeGetter.TimeGetter;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ReturnedListController {
    @FXML
    private TableColumn<BookLoan, String> usernameColumn;
    @FXML
    private TableColumn<BookLoan, String> bookTitleColumn;
    @FXML
    private TableColumn<BookLoan, String> loanDateColumn;
    @FXML
    private TableColumn<BookLoan, String> dueDateColumn;
    @FXML
    private TableColumn<BookLoan, String> statusColumn;
    @FXML
    private TableColumn<BookLoan, Void> optionColumn;
    @FXML
    private StackPane mainStackPane;
    @FXML
    private TextField searchTitleField;
    @FXML
    private TableView<BookLoan> bookLoansTable;

    /**
     * all books list
     */
    private static final List<BookLoan> bookLoansList = new LinkedList<>();

    public static void addBookLoanToList(BookLoan bookLoan) {
        bookLoansList.add(bookLoan);
    }

    public void addBookLoansListToTable(List<BookLoan> bookLoansList) {
        bookLoansTable.getItems().clear();
        LocalDate currentTime = TimeGetter.getCurrentTime().toLocalDate();
        for (BookLoan bookLoan : bookLoansList) {
            if (currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).compareTo(bookLoan.getDueDate()) > 0) {
                bookLoan.setStatus("Overdue");
            }
            bookLoansTable.getItems().add(bookLoan);
        }
    }

    /**
     * search book loan by title, limit = 0 mean no limit
     */
    private  List<BookLoan> searchBookLoansList(String title) {
        return BookLoan.fuzzySearch(bookLoansList, title, 0, 0);
    }

    public  void updateTable() {
        if (searchTitleField.getText().isEmpty()) {
            addBookLoansListToTable(bookLoansList);
        } else {
            addBookLoansListToTable(searchBookLoansList(searchTitleField.getText()));
        }
    }

    private void initOptionColumn() {
        optionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<BookLoan, Void> call(TableColumn<BookLoan, Void> param) {
                return new TableCell<>() {
                    private final JFXButton informationButton = new JFXButton();

                    {
                        //create record Image
                        ImageView recordImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/information.png")))); // Đường dẫn tới ảnh
                        recordImage.setFitWidth(20); // Đặt kích thước cho ảnh
                        recordImage.setFitHeight(20);

                        //set informationButton

                        informationButton.setPadding(Insets.EMPTY);
                        informationButton.setRipplerFill(Color.WHITE);
                        informationButton.setCursor(Cursor.HAND);
                        informationButton.setGraphic(recordImage);
                        informationButton.setOnAction(event -> {
                            //lấy ô hiện tại đang chọn
                            BookLoan currentBookLoan = getTableView().getItems().get(getIndex());
                            //load book information
                            Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
                            FXMLLoader recordBookReturnLoader = new FXMLLoader(getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/BorrowedList/RecordBookReturn/record-book-return.fxml"));
                            try {
                                mainStackPane.getChildren().add(recordBookReturnLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            RecordBookReturnController recordBookReturnController = recordBookReturnLoader.getController();
                            recordBookReturnController.setBookLoan(currentBookLoan);

                            //ẩn nút ghi nhận
                            recordBookReturnController.recordButton.setManaged(false);

                            //ẩn nút cancel
                            recordBookReturnController.cancelButton.setManaged(false);

                            //back button event
                            recordBookReturnController.backButton.setManaged(true);
                            recordBookReturnController.backButton.setOnMouseClicked(backMouseEvent -> {
                                if (backMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    mainStackPane.getChildren().removeLast();
                                    mainStackPane.getChildren().add(savePane);

                                    //set lại nút ghi nhận
                                    recordBookReturnController.recordButton.setManaged(true);

                                    //set lại nút cancel
                                    recordBookReturnController.cancelButton.setManaged(true);

                                    //ẩn nút back
                                    recordBookReturnController.backButton.setManaged(false);
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
                            setGraphic(informationButton);
                        }

                        // Căn giữa ảnh trong ô
                        setStyle("-fx-alignment: CENTER;");
                    }
                };
            }
        });
    }

    private void initStatusColumn() {
        statusColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<BookLoan, String> call(TableColumn<BookLoan, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item);
                            if ("On Time".equals(item)) {
                                setTextFill(Color.GREEN); // Nếu giá trị là "On Time", set màu đỏ
                            } else {
                                setTextFill(Color.RED); // Nếu không, set màu xanh "Overdue"
                            }
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Lắng nghe thay đổi của text
        searchTitleField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTable();
        });

        initOptionColumn();
        initStatusColumn();
    }

}

