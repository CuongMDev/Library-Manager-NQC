package com.example.librarymanagernqc.Login;

import com.example.librarymanagernqc.Objects.AccountChecker.AccountChecker;
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

public class RecoveryKey {
    @FXML
    public Button backToLoginButton;
    @FXML
    private Text errorText;
    @FXML
    private TextField key1;
    @FXML
    private TextField key2;
    @FXML
    private TextField key3;
    @FXML
    private TextField key4;
    @FXML
    private TextField key5;
    @FXML
    private TextField key6;
    @FXML
    private TextField key7;
    @FXML
    private TextField key8;

    @FXML
    private StackPane mainStackPane;
    @FXML
    public JFXButton backButton;

    private String username;

    @FXML
    private void onRecoverMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            String key = String.format("%s-%s-%s-%s-%s-%s-%s-%s", key1, key2, key3, key4, key5, key6, key7, key8);
            if (AccountChecker.checkValidKey(username, key)) {
                Pane saveLoginPane = (Pane) mainStackPane.getChildren().removeLast();
                FXMLLoader newPassword = new FXMLLoader(getClass().getResource("new-password.fxml"));
                try {
                    mainStackPane.getChildren().add(newPassword.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                NewPassword newPasswordController = newPassword.getController();
                //set username
                newPasswordController.setUsername(username);
                newPasswordController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                    if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                        mainStackPane.getChildren().removeLast();
                        mainStackPane.getChildren().add(saveLoginPane);

                        //click by left mouse
                        backToLoginButton.fireEvent(mouseEvent);
                    }
                });

                //back button
                newPasswordController.backButton.setOnMouseClicked(backToLoginMouseEvent -> {
                    if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                        mainStackPane.getChildren().removeLast();
                        mainStackPane.getChildren().add(saveLoginPane);
                    }
                });
            } else {
                errorText.setText(AccountChecker.getErrorMessage());
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
