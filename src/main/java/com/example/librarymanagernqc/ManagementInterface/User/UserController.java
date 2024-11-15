package com.example.librarymanagernqc.ManagementInterface.User;

import com.example.librarymanagernqc.ManagementInterface.BorrowedList.BorrowedListController;
import com.example.librarymanagernqc.ManagementInterface.Document.BookInformation.BookInformationController;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.ManagementInterface.User.AddUser.AddUserController;
import com.example.librarymanagernqc.ManagementInterface.User.RecordBookLoan.RecordBookLoanController;
import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.User.User;
import com.example.librarymanagernqc.database.Controller.BookDatabaseController;
import com.example.librarymanagernqc.database.Controller.UserDatabaseController;
import com.example.librarymanagernqc.database.DAO.BookDAO;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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

public class UserController {
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

    private void deleteUserFromList(User user) {
        boolean isDeletedFromDb = UserDatabaseController.deleteUser(user);  // Xóa khỏi database

        if (isDeletedFromDb) {
            usersList.remove(user);  // Xóa khỏi danh sách sau khi xóa thành công trong database
            updateTable();  // Cập nhật bảng để phản ánh sự thay đổi
        } else {
            System.out.println("Failed to delete book from database.");  // Thông báo nếu xóa không thành công
        }
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

    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        citizenIdColumn.setCellValueFactory(new PropertyValueFactory<>("citizenId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

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
                            Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
                            FXMLLoader userInfoLoader = new FXMLLoader(getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/User/AddUser/add-user.fxml"));
                            try {
                                mainStackPane.getChildren().add(userInfoLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            AddUserController userInfoController = userInfoLoader.getController();
                            userInfoController.setUser(currentUser);
                            userInfoController.setType(AddUserController.Type.EDIT);

                            //save button event
                            userInfoController.addButton.setOnMouseClicked(addMouseEvent -> {
                                if (addMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    if (userInfoController.checkValidUser()) {
                                        currentUser.setUser(userInfoController.getUser());
                                        userTable.refresh();

                                        mainStackPane.getChildren().removeLast();
                                        mainStackPane.getChildren().add(savePane);
                                    }
                                }
                            });

                            //cancel button event
                            userInfoController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
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
                            //lấy ô hiện tại đang chọn
                            User user = getTableView().getItems().get(getIndex());

                            // Xóa người dùng khỏi database
                            boolean isDeleted = UserDatabaseController.deleteUser(user);
                            if (isDeleted) {
                                // Nếu xóa thành công, xóa người dùng khỏi TableView
                                getTableView().getItems().remove(user);
                            }

                            System.out.println(UserDatabaseController.deleteUser(user));
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
                            Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
                            FXMLLoader bookLoanInfoLoader = new FXMLLoader(getClass().getResource("RecordBookLoan/record-book-loan.fxml"));
                            try {
                                mainStackPane.getChildren().add(bookLoanInfoLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            RecordBookLoanController bookLoanInfoController = bookLoanInfoLoader.getController();
                            bookLoanInfoController.setUser(currentUser);

                            bookLoanInfoController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    mainStackPane.getChildren().removeLast();
                                    mainStackPane.getChildren().add(savePane);
                                }
                            });

                            bookLoanInfoController.recordButton.setOnMouseClicked(recordButtonMouseEvent -> {
                                if (recordButtonMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    if (bookLoanInfoController.checkValidBookLoan()) {
                                        BookLoan getBookLoan = bookLoanInfoController.getBookLoan();
                                        BorrowedListController.addBookLoanToList(getBookLoan);
                                        //giảm số lượng sách
                                        DocumentController.changeBookQuantity(Objects.requireNonNull(DocumentController.searchBookById(getBookLoan.getBookId())), -getBookLoan.getLoanQuantity());
                                        mainStackPane.getChildren().removeLast();
                                        mainStackPane.getChildren().add(savePane);
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
            Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
            FXMLLoader addUserLoader = new FXMLLoader(getClass().getResource("AddUser/add-user.fxml"));
            mainStackPane.getChildren().add(addUserLoader.load());

            AddUserController addUserController = addUserLoader.getController();
            addUserController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                    mainStackPane.getChildren().removeLast();
                    mainStackPane.getChildren().add(savePane);
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

                        mainStackPane.getChildren().removeLast();
                        mainStackPane.getChildren().add(savePane);

                        userTable.getItems().add(addUserController.getUser());
                    }
                }
            });
        }
    }
}
