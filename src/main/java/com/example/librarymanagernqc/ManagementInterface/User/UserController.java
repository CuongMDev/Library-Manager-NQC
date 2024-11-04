package com.example.librarymanagernqc.ManagementInterface.User;

import com.example.librarymanagernqc.ManagementInterface.User.AddUser.AddUserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class UserController {
    @FXML
    private StackPane mainStackPane;

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
    }
}
