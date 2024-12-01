package com.example.librarymanagernqc.Login;

import com.example.librarymanagernqc.Objects.AccountChecker.AccountChecker;
import com.example.librarymanagernqc.database.Controller.AdminDatabaseController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class SignUpController {
    @FXML
    private StackPane mainStackPane;
    @FXML
    private TextField usernameField;
    @FXML
    private JFXButton nextButton;
    @FXML
    public Button backToLoginButton;
    @FXML
    private Text errorText;

    @FXML
    private void onNextButtonMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            boolean checkUserNameExists = AdminDatabaseController.isUserExists(usernameField.getText());
            if (AccountChecker.checkUsernameCondition(usernameField.getText()) && !checkUserNameExists) {
                Pane saveLoginPane = (Pane) mainStackPane.getChildren().removeLast();
                FXMLLoader recoveryKeyLoader = new FXMLLoader(getClass().getResource("recovery-key.fxml"));
                try {
                    mainStackPane.getChildren().add(recoveryKeyLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                AdminDatabaseController.insertAdminAcount(usernameField.getText(), "00000000");

                RecoveryKeyController recoveryKeyController = recoveryKeyLoader.getController();
                recoveryKeyController.setUsername(usernameField.getText());
                recoveryKeyController.setType(RecoveryKeyController.Type.GIVE_KEY);
                recoveryKeyController.giveKey(usernameField.getText());

                recoveryKeyController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                    if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                        mainStackPane.getChildren().removeLast();
                        mainStackPane.getChildren().add(saveLoginPane);
                    }
                });

                recoveryKeyController.backButton.setOnMouseClicked(backMouseEvent -> {
                    if (backMouseEvent.getButton() == MouseButton.PRIMARY) {
                        mainStackPane.getChildren().removeLast();
                        mainStackPane.getChildren().add(saveLoginPane);
                    }
                });
            } else {
                errorText.setText(AccountChecker.getErrorMessage());
            }
        }
    }
}
