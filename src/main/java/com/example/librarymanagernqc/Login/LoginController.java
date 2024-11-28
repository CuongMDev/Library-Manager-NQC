package com.example.librarymanagernqc.Login;

import com.example.librarymanagernqc.ManagementInterface.MainPaneController;
import com.example.librarymanagernqc.Objects.AccountChecker.AccountChecker;
import com.example.librarymanagernqc.database.DatabaseLoader;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private JFXButton loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private StackPane rightFormStackPane;
    @FXML
    private Text errorText;

    private void enterApp() {
        try {
            // Get the current stage
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            // Load the new scene
            FXMLLoader mainPaneLoader = new FXMLLoader(getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/main-pane.fxml"));
            // Set the new scene
            stage.setScene(new Scene(mainPaneLoader.load()));
            stage.show();
            stage.centerOnScreen();

            MainPaneController mainPaneController = mainPaneLoader.getController();
            mainPaneController.logoutButton.setOnMouseClicked(logoutMouseEvent -> {
                if (logoutMouseEvent.getButton() == MouseButton.PRIMARY) {
                    // Close the current stage
                    stage.close();

                    // Set the new scene
                    try {
                        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("login.fxml")).load()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.show();
                    stage.centerOnScreen();
                }
            });

        } catch (Exception e) {
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    private void onLoginMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (AccountChecker.checkValidAccount(usernameField.getText(), passwordField.getText())) {//account valid
                //Kiểm tra đăng nhập lần đầu
                if (AccountChecker.checkDefaultPassword(passwordField.getText())) {
                    Pane saveLoginPane = (Pane) rightFormStackPane.getChildren().removeLast();
                    FXMLLoader recoveryKeyLoader = new FXMLLoader(getClass().getResource("recovery-key.fxml"));
                    try {
                        rightFormStackPane.getChildren().add(recoveryKeyLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    RecoveryKeyController recoveryKeyController = recoveryKeyLoader.getController();
                    recoveryKeyController.setUsername(usernameField.getText());
                    recoveryKeyController.setType(RecoveryKeyController.Type.GIVE_KEY);
                    recoveryKeyController.giveKey(usernameField.getText());

                    recoveryKeyController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                        if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                            rightFormStackPane.getChildren().removeLast();
                            rightFormStackPane.getChildren().add(saveLoginPane);
                        }
                    });

                    recoveryKeyController.backButton.setOnMouseClicked(backMouseEvent -> {
                        if (backMouseEvent.getButton() == MouseButton.PRIMARY) {
                            rightFormStackPane.getChildren().removeLast();
                            rightFormStackPane.getChildren().add(saveLoginPane);
                        }
                    });
                } else if (!AccountChecker.checkValidPassword(passwordField.getText())) {
                    errorText.setText(AccountChecker.getErrorMessage());
                } else {
                    if (DatabaseLoader.loadDataFromDatabase()) { //load data successfully
                        enterApp();
                    } else {
                        errorText.setText(DatabaseLoader.getErrorMessage());
                    }
                }
            } else {
                errorText.setText(AccountChecker.getErrorMessage());
            }
        }
    }

    @FXML
    private void OnForgotPasswordMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Pane saveLoginPane = (Pane) rightFormStackPane.getChildren().removeLast();
            FXMLLoader forgotPasswordLoader = new FXMLLoader(getClass().getResource("forgot-password.fxml"));
            try {
                rightFormStackPane.getChildren().add(forgotPasswordLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ForgotPasswordController forgotPasswordController = forgotPasswordLoader.getController();
            forgotPasswordController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                    rightFormStackPane.getChildren().removeLast();
                    rightFormStackPane.getChildren().add(saveLoginPane);
                }
            });
        }
    }
}
