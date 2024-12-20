package com.example.librarymanagernqc.ManagementInterface.BorrowedList;

import com.example.librarymanagernqc.AbstractClass.Controller;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.ManagementInterface.ReturnedList.ReturnedListController;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.ManagementInterface.BorrowedList.RecordBookReturn.RecordBookReturnController;
import com.example.librarymanagernqc.TimeGetter.TimeGetter;
import com.example.librarymanagernqc.database.Controller.BorrowedListDatabaseController;
import com.example.librarymanagernqc.database.Controller.ReturnedListDatabaseController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BorrowedListController extends Controller {
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

    public static void setAllBookLoanList(List<BookLoan> newbookLoanList) {
//        bookLoansList = newbookLoanList;
        bookLoansList.clear();
        bookLoansList.addAll(newbookLoanList);
    }


    public static void removeBookLoanFromList(BookLoan bookLoan) {
        bookLoansList.remove(bookLoan);
    }

    private void addBookLoansListToTable(List<BookLoan> bookLoansList) {
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
    private List<BookLoan> searchBookLoansList(String title) {
        return BookLoan.fuzzySearch(bookLoansList, title, 0, 0);
    }

    public void updateTable() {
        if (searchTitleField.getText().isEmpty()) {
            addBookLoansListToTable(bookLoansList);
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
                    private final JFXButton recordButton = new JFXButton();

                    {
                        //create record Image
                        ImageView recordImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/record.png")))); // Đường dẫn tới ảnh
                        recordImage.setFitWidth(20); // Đặt kích thước cho ảnh
                        recordImage.setFitHeight(20);

                        //set recordButton

                        recordButton.setPadding(Insets.EMPTY);
                        recordButton.setRipplerFill(Color.WHITE);
                        recordButton.setCursor(Cursor.HAND);
                        recordButton.setGraphic(recordImage);
                        recordButton.setOnAction(event -> {
                            //lấy ô hiện tại đang chọn
                            BookLoan currentBookLoan = getTableView().getItems().get(getIndex());
                            //load book information
                            RecordBookReturnController recordBookReturnController;
                            try {
                                recordBookReturnController = (RecordBookReturnController) Controller.init(getStage(), getClass().getResource("RecordBookReturn/record-book-return.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            switchPane(mainStackPane, recordBookReturnController.getParent());

                            recordBookReturnController.setBookLoan(currentBookLoan);

                            //record button event
                            recordBookReturnController.recordButton.setOnMouseClicked(recordMouseEvent -> {
                                if (recordMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    //cập nhật tình trạng sách và tiền fạt
                                    recordBookReturnController.updateBookLoan(currentBookLoan);

                                    // thêm thông tin trả sách vào database
                                    boolean isInserted = ReturnedListDatabaseController.getInstance().insertBookReturn(currentBookLoan);
                                    if (isInserted) {
                                        System.out.println("thêm thông tin trả sách vào database thành công");
                                        //thêm thông tin trả sách
                                        ReturnedListController.addBookLoanToList(currentBookLoan);
                                    } else {
                                        System.out.println("thêm thông tin trả sách vào database thất bại");
                                    }

                                    boolean isDelete = BorrowedListDatabaseController.getInstance().deleteBookLoanById(currentBookLoan);
                                    if (isDelete) {
                                        System.out.println("xóa thông tin mượn sách khỏi database thành công");
                                        //xóa sách khỏi danh sách mượn
                                        removeBookLoanFromList(currentBookLoan);
                                    } else {
                                        System.out.println("xóa thông tin mượn sách database thất bại");
                                    }


                                    //add lại số lượng vào document
                                    DocumentController.changeBookQuantity(currentBookLoan.getBookId(), currentBookLoan.getLoanQuantity());

                                    switchToSavePane(mainStackPane);
                                    updateTable();
                                }
                            });

                            //cancel button event
                            recordBookReturnController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
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
                            setGraphic(recordButton);
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

    public static List<BookLoan> getOverdueList() {
        List<BookLoan> overdueList = new ArrayList<>();
        for (BookLoan bookLoan : bookLoansList) {
            if (bookLoan.getStatus().equals("Overdue")) {
                overdueList.add(bookLoan);
            }
        }
        return overdueList;
    }

}
