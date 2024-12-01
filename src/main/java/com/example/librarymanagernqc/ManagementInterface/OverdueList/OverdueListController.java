package com.example.librarymanagernqc.ManagementInterface.OverdueList;

import com.example.librarymanagernqc.ManagementInterface.BorrowedList.BorrowedListController;
import com.example.librarymanagernqc.ManagementInterface.BorrowedList.RecordBookReturn.RecordBookReturnController;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.ManagementInterface.ReturnedList.ReturnedListController;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.TimeGetter.TimeGetter;
import com.example.librarymanagernqc.database.Controller.BorrowedListDatabaseController;
import com.example.librarymanagernqc.database.Controller.ReturnedListDatabaseController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OverdueListController {
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
    private TableView<BookLoan> overdueListTable;


    private static final List<BookLoan> overdueList = new ArrayList<>();

    public static void addBookLoanToList(BookLoan bookLoan) {
        overdueList.add(bookLoan);
    }

    public static void setAllOverdueList(List<BookLoan> newOverdueList) {
//        overdueList = newOverdueList;
        overdueList.clear();
        overdueList.addAll(newOverdueList);
    }


    public static void removeBookLoanFromList(BookLoan bookLoan) {
        overdueList.remove(bookLoan);
    }

    private void addOverdueListToTable(List<BookLoan> overdueList) {
        overdueListTable.getItems().clear();
        for (BookLoan bookLoan : OverdueListController.overdueList) {
            overdueListTable.getItems().add(bookLoan);
        }
    }

    /**
     * search book loan by title, limit = 0 mean no limit
     */
    private List<BookLoan> searchBookLoansList(String title) {
        return BookLoan.fuzzySearch(overdueList, title, 0, 0);
    }

    public void updateTable() {
        setAllOverdueList(BorrowedListController.getOverdueList());
        if (searchTitleField.getText().isEmpty()) {
            addOverdueListToTable(overdueList);
        } else {
            addOverdueListToTable(searchBookLoansList(searchTitleField.getText()));
        }
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
                            Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
                            FXMLLoader recordBookReturnLoader = new FXMLLoader(getClass().getResource("RecordBookReturn/record-book-return.fxml"));
                            try {
                                mainStackPane.getChildren().add(recordBookReturnLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            RecordBookReturnController recordBookReturnController = recordBookReturnLoader.getController();
                            recordBookReturnController.setBookLoan(currentBookLoan);

                            //record button event
                            recordBookReturnController.recordButton.setOnMouseClicked(recordMouseEvent -> {
                                if (recordMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    //cập nhật tình trạng sách và tiền fạt
                                    recordBookReturnController.updateBookLoan(currentBookLoan);

                                    // thêm thông tin trả sách vào database
                                    boolean isInserted = ReturnedListDatabaseController.insertBookReturn(currentBookLoan);
                                    if (isInserted) {
                                        System.out.println("thêm thông tin trả sách vào database thành công");
                                        //thêm thông tin trả sách
                                        ReturnedListController.addBookLoanToList(currentBookLoan);
                                    }
                                    else{
                                        System.out.println("thêm thông tin trả sách vào database thất bại");
                                    }

                                    boolean isDelete = BorrowedListDatabaseController.deleteBookLoanById(currentBookLoan);
                                    if(isDelete){
                                        System.out.println("xóa thông tin mượn sách khỏi database thành công");
                                        //xóa sách khỏi danh sách mượn
                                        removeBookLoanFromList(currentBookLoan);
                                    }
                                    else{
                                        System.out.println("xóa thông tin mượn sách database thất bại");
                                    }


                                    //add lại số lượng vào document
                                    DocumentController.changeBookQuantity(currentBookLoan.getBookId(), currentBookLoan.getLoanQuantity());

                                    mainStackPane.getChildren().removeLast();
                                    mainStackPane.getChildren().add(savePane);
                                    updateTable();
                                }
                            });

                            //cancel button event
                            recordBookReturnController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    mainStackPane.getChildren().removeLast();
                                    mainStackPane.getChildren().add(savePane);
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
}
