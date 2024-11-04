package com.example.librarymanagernqc.ManagementInterface.User;

import com.example.librarymanagernqc.ManagementInterface.User.AddUser.AddUserController;
import com.example.librarymanagernqc.User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

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
    private TableView<User> userTable;

    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        citizenIdColumn.setCellValueFactory(new PropertyValueFactory<>("citizenId"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    @FXML
    private void onAddMouseClicked() throws IOException {
        Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
        FXMLLoader addUserLoader = new FXMLLoader(getClass().getResource("AddUser/add-user.fxml"));
        mainStackPane.getChildren().add(addUserLoader.load());

        AddUserController addUserController = addUserLoader.getController();
        addUserController.cancelButton.setOnMouseClicked(event -> {
            mainStackPane.getChildren().removeLast();
            mainStackPane.getChildren().add(savePane);
        });
        addUserController.addButton.setOnMouseClicked(event -> {
            if (addUserController.checkAndHandleValidUser()) {
                mainStackPane.getChildren().removeLast();
                mainStackPane.getChildren().add(savePane);

                userTable.getItems().add(addUserController.getUser());
            }
        });
    }
}
