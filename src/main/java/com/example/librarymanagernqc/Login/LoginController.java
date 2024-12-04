package com.example.librarymanagernqc.Login;

import com.example.librarymanagernqc.AbstractClass.Controller;
import com.example.librarymanagernqc.ManagementInterface.MainPaneController;
import com.example.librarymanagernqc.Objects.AccountController.AccountController;
import com.example.librarymanagernqc.database.DatabaseLoader;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Controller {
    @FXML
    private JFXButton loginButton;
    @FXML
    private Button SignUpButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private StackPane rightFormStackPane;
    @FXML
    private Text errorText;

    public void initialize() {

    }

    private void enterApp() {
        try {
            // Get the current stage
            if (getStage() == null) {
                setStage(new Stage());
            }
            Stage stage = getStage();
            stage.close();

            // Load the new scene
            MainPaneController mainPaneController = (MainPaneController)Controller.init(stage, getClass().getResource("/com/example/librarymanagernqc/ManagementInterface/main-pane.fxml"));
            // Set the new scene
            stage.setScene(new Scene(mainPaneController.getParent()));
            stage.show();
            stage.centerOnScreen();

            mainPaneController.init();
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
            e.printStackTrace();
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    private void onLoginMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (AccountController.getInstance().checkValidAccount(usernameField.getText(), passwordField.getText())) {//account valid
                //Kiểm tra đăng nhập lần đầu
                if (AccountController.getInstance().checkDefaultPassword(passwordField.getText())) {
                    RecoveryKeyController recoveryKeyController;
                    try {
                        recoveryKeyController = (RecoveryKeyController) Controller.init(getStage(), getClass().getResource("recovery-key.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    switchPane(rightFormStackPane, recoveryKeyController.getParent());
                    recoveryKeyController.setUsername(usernameField.getText());
                    recoveryKeyController.setType(RecoveryKeyController.Type.GIVE_KEY);
                    recoveryKeyController.giveKey(usernameField.getText());

                    recoveryKeyController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                        if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                            switchToSavePane(rightFormStackPane);
                        }
                    });

                    recoveryKeyController.backButton.setOnMouseClicked(backMouseEvent -> {
                        if (backMouseEvent.getButton() == MouseButton.PRIMARY) {
                            switchToSavePane(rightFormStackPane);
                        }
                    });
                } else if (!AccountController.getInstance().checkValidPassword(passwordField.getText())) {
                    errorText.setText(AccountController.getInstance().getErrorMessage());
                } else {
                    if (DatabaseLoader.getInstance().loadDataFromDatabase()) { //load data successfully
                        enterApp();
                    } else {
                        errorText.setText(DatabaseLoader.getInstance().getErrorMessage());
                    }
                }
            } else {
                errorText.setText(AccountController.getInstance().getErrorMessage());
            }
        }
    }

    @FXML
    private void OnForgotPasswordMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ForgotPasswordController forgotPasswordController;
            try {
                forgotPasswordController = (ForgotPasswordController)Controller.init(getStage(), getClass().getResource("forgot-password.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            switchPane(rightFormStackPane, forgotPasswordController.getParent());
            forgotPasswordController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                    switchToSavePane(rightFormStackPane);
                }
            });
        }
    }

    @FXML
    private void onSignUpMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            SignUpController signUpController;
            try {
                signUpController = (SignUpController)Controller.init(getStage(), getClass().getResource("sign-up.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            switchPane(rightFormStackPane, signUpController.getParent());
            signUpController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
               if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                   switchToSavePane(rightFormStackPane);
               }
            });
        }
    }
}
