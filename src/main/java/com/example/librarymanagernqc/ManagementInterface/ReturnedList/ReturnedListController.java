package com.example.librarymanagernqc.ManagementInterface.ReturnedList;

import com.example.librarymanagernqc.AbstractClass.Controller;
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
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ReturnedListController extends Controller {
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
    private static final List<BookLoan> bookReturnsList = new LinkedList<>();

    public static void addBookLoanToList(BookLoan bookLoan) {
        bookReturnsList.add(bookLoan);
    }

    public static void setAllBookReturnList(List<BookLoan> newbookReturnList) {
        bookReturnsList.clear();
        bookReturnsList.addAll(newbookReturnList);
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
        return BookLoan.fuzzySearch(bookReturnsList, title, 0, 0);
    }

    public  void updateTable() {
        if (searchTitleField.getText().isEmpty()) {
            addBookLoansListToTable(bookReturnsList);
        } else {
            addBookLoansListToTable(searchBookLoansList(searchTitleField.getText()));
        }
    }

    @Override
    public void refresh() {
        updateTable();
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
                            RecordBookReturnController recordBookReturnController;
                            try {
                                recordBookReturnController = (RecordBookReturnController) Controller.init(getStage(), getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/BorrowedList/RecordBookReturn/record-book-return.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            switchPane(mainStackPane, recordBookReturnController.getParent());

                            recordBookReturnController.setBookLoan(currentBookLoan);
                            //set type infomation
                            recordBookReturnController.setType(RecordBookReturnController.Type.INFOMATION);

                            //back button event
                            recordBookReturnController.backButton.setManaged(true);
                            recordBookReturnController.backButton.setOnMouseClicked(backMouseEvent -> {
                                if (backMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    switchToSavePane(mainStackPane);
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

