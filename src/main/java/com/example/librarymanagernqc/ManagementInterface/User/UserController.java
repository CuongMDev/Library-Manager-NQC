package com.example.librarymanagernqc.ManagementInterface.User;

import com.example.librarymanagernqc.AbstractClass.Controller;
import com.example.librarymanagernqc.ManagementInterface.BorrowedList.BorrowedListController;
import com.example.librarymanagernqc.ManagementInterface.Document.BookInformation.BookInformationController;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.ManagementInterface.User.AddUser.AddUserController;
import com.example.librarymanagernqc.ManagementInterface.User.RecordBookLoan.RecordBookLoanController;
import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.User.User;
import com.example.librarymanagernqc.database.Controller.BookDatabaseController;
import com.example.librarymanagernqc.database.Controller.BorrowedListDatabaseController;
import com.example.librarymanagernqc.database.Controller.RecentBorrowedDatabaseController;
import com.example.librarymanagernqc.database.Controller.UserDatabaseController;
import com.example.librarymanagernqc.database.DAO.BookDAO;
import com.example.librarymanagernqc.database.DAO.BorrowedListDAO;
import com.example.librarymanagernqc.database.DAO.UserDAO;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class UserController extends Controller {
    @FXML
    private StackPane mainStackPane;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> citizenIdColumn;
    @FXML
    private TableColumn<User, String> fullNameColumn;
    @FXML
    private TableColumn<User, String> genderColumn;
    @FXML
    private TableColumn<User, String> dateOfBirthColumn;
    @FXML
    private TableColumn<User, String> phoneNumberColumn;
    @FXML
    private TableColumn<User, Void> optionColumn;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TextField searchField;

    /**
     * all users list
     */
    private static List<User> usersList = new LinkedList<>();

    private void addUsersListToTable(List<User> usersList) {
        userTable.getItems().clear();
        for (User user : usersList) {
            userTable.getItems().add(user);
        }
    }

    public static void setAllUsersList(List<User> newUsersList) {
        usersList = newUsersList;
    }


    /**
     * search book by title, limit = 0 mean no limit
     */
    public static List<User> searchUsersList(String title, int limit) {
        return User.fuzzySearch(usersList, title, 0, limit);
    }

    public void updateTable() {
        if (searchField.getText().isEmpty()) {
            addUsersListToTable(usersList);
        } else {
            addUsersListToTable(searchUsersList(searchField.getText(), 0));
        }
    }

    @Override
    public void refresh() {
        updateTable();
    }

    public void recordBookLoan(BookLoan bookLoan) {
        boolean isInsertedBookLoan = BorrowedListDatabaseController.getInstance().insertBookLoan(bookLoan);
        if (isInsertedBookLoan) {
            System.out.println("Thêm thông tin mượn sách vào database thành công");
            BorrowedListController.addBookLoanToList(bookLoan);

            Book getBook = Objects.requireNonNull(DocumentController.searchBookById(bookLoan.getBookId()));

            //giảm số lượng sách
            DocumentController.changeBookQuantity(getBook.getId(), -bookLoan.getLoanQuantity());

            if(!RecentBorrowedDatabaseController.getInstance().isRecentBookExists(getBook.getId())) {
                boolean isInsertRecentBorrowed = RecentBorrowedDatabaseController.getInstance().insertRecentBorrowed(getBook);

                if (isInsertRecentBorrowed) {
                    DocumentController.addBookToRecentList(getBook);
                }
                else{
                    System.out.println("Thêm thông tin sách mượn gần nhất vào database thất bại");
                }
            }

        }
        else{
            System.out.println("Thêm thông tin sách mượn vào database thất bại");
        }
    }

    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        citizenIdColumn.setCellValueFactory(new PropertyValueFactory<>("citizenId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Lắng nghe thay đổi của text
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTable();
        });

        optionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<>() {
                    private final JFXButton editButton = new JFXButton();
                    {
                        //create edit Image
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
                            User currentUser = getTableView().getItems().get(getIndex());
                            //load user information
                            AddUserController addUserController;
                            try {
                                addUserController = (AddUserController) Controller.init(getStage(), getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/User/AddUser/add-user.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            switchPane(mainStackPane, addUserController.getParent());

                            addUserController.setUser(currentUser);
                            addUserController.setType(AddUserController.Type.EDIT);

                            //save button event
                            addUserController.addButton.setOnMouseClicked(addMouseEvent -> {
                                if (addMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    if (addUserController.checkValidUser()) {
                                        currentUser.setUser(addUserController.getUser());

                                        boolean isUpdated = UserDatabaseController.getInstance().updateUser(currentUser);
                                        if (isUpdated) {
                                            System.out.println("User updated successfully in database");
                                        } else {
                                            System.out.println("Failed to update user in database");
                                        }
                                        userTable.refresh();

                                        switchToSavePane(mainStackPane);
                                    }
                                }
                            });

                            //cancel button event
                            addUserController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    switchToSavePane(mainStackPane);
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
                            //lấy ô hiện tại đang chọn
                            User user = getTableView().getItems().get(getIndex());

                            // kiểm tra nếu người dùng đang mượn sách thì không được xóa
                            if(BorrowedListDatabaseController.getInstance().isBookLoanExistBymemberName(user.getUsername())) {
                                // Hiển thị thông báo
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Can't delete a user");
                                alert.setHeaderText(null);
                                alert.setContentText("Unable to delete a user because the user is borrowing a book");
                                alert.showAndWait();
                            }
                            else{
                                // Xóa người dùng khỏi database
                                boolean isDeleted = UserDatabaseController.getInstance().deleteUser(user);
                                if (isDeleted) {
                                    // Nếu xóa thành công, xóa người dùng khỏi TableView
                                    getTableView().getItems().remove(user);
                                }
                            }
                        });
                    }

                    private final JFXButton borrowButton = new JFXButton();
                    {
                        //create borrow Image
                        ImageView borrowImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/borrow.png")))); // Đường dẫn tới ảnh
                        borrowImage.setFitWidth(20); // Đặt kích thước cho ảnh
                        borrowImage.setFitHeight(20);


                        //set borrowButton
                        borrowButton.setPadding(Insets.EMPTY);
                        borrowButton.setRipplerFill(Color.WHITE);
                        borrowButton.setCursor(Cursor.HAND);
                        borrowButton.setGraphic(borrowImage);
                        borrowButton.setOnAction(event -> {
                            //lấy ô hiện tại đang chọn
                            User currentUser = getTableView().getItems().get(getIndex());
                            //load record book loan information
                            RecordBookLoanController bookLoanInfoController;
                            try {
                                bookLoanInfoController = (RecordBookLoanController) Controller.init(getStage(), getClass().getResource("RecordBookLoan/record-book-loan.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            switchPane(mainStackPane, bookLoanInfoController.getParent());

                            bookLoanInfoController.setUser(currentUser);

                            bookLoanInfoController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    switchToSavePane(mainStackPane);
                                }
                            });

                            bookLoanInfoController.recordButton.setOnMouseClicked(recordButtonMouseEvent -> {
                                if (recordButtonMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    if (bookLoanInfoController.checkValidBookLoan()) {
                                        BookLoan getBookLoan = bookLoanInfoController.getBookLoan();

                                        recordBookLoan(getBookLoan);

                                        switchToSavePane(mainStackPane);
                                    }
                                }
                            });
                        });

                    }

                    private final HBox buttonsBox = new HBox(7, editButton, borrowButton, deleteButton);
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

    @FXML
    private void onAddMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            AddUserController addUserController = (AddUserController) Controller.init(getStage(), getClass().getResource("AddUser/add-user.fxml"));
            switchPane(mainStackPane, addUserController.getParent());

            addUserController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                    switchToSavePane(mainStackPane);
                }
            });
            addUserController.addButton.setOnMouseClicked(addUserMouseEvent -> {
                if (addUserMouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (addUserController.checkValidUser()) {


                        // Kiểm tra người dùng đã có trong bảng chưa
                        boolean userExistsInTable = userTable.getItems().stream()
                        .anyMatch(existingUser -> existingUser.getUsername().equals(addUserController.getUser().getUsername()) || existingUser.getCitizenId().equals(addUserController.getUser().getCitizenId()));
                        // Không cho phép thêm người dùng vào bảng
                        if (userExistsInTable) {
                            System.out.println("User already exists in the table");
                            return;  // Không cho phép thêm người dùng vào bảng
                        }

                        switchToSavePane(mainStackPane);

                        userTable.getItems().add(addUserController.getUser());
                    }
                }
            });
        }
    }
}
