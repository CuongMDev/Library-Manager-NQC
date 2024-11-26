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

public class RecoveryKeyController {
    public enum Type {
        GIVE_KEY,
        SET_KEY
    }
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
    public JFXButton recoverButton;
    @FXML
    private StackPane mainStackPane;
    @FXML
    public JFXButton backButton;

    private String username;

    public void setType(Type type) {
        if (type == Type.GIVE_KEY) {
            recoverButton.setText("Confirm");

            key1.setEditable(false);
            key2.setEditable(false);
            key3.setEditable(false);
            key4.setEditable(false);
            key5.setEditable(false);
            key6.setEditable(false);
            key7.setEditable(false);
            key8.setEditable(false);
        }
    }

    @FXML
    private void onRecoverMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            String key = String.format("%s-%s-%s-%s-%s-%s-%s-%s", key1.getText(), key2.getText(), key3.getText(), key4.getText(), key5.getText(), key6.getText(), key7.getText(), key8.getText());
            if (AccountChecker.checkValidKey(username, key)) {
                Pane saveLoginPane = (Pane) mainStackPane.getChildren().removeLast();
                FXMLLoader newPassword = new FXMLLoader(getClass().getResource("new-password.fxml"));
                try {
                    mainStackPane.getChildren().add(newPassword.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                NewPasswordController newPasswordController = newPassword.getController();
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
