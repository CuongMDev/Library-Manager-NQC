package com.example.librarymanagernqc.Login;

import com.example.librarymanagernqc.ManagementInterface.MainPaneController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private JFXButton loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private StackPane rightFormStackPane;

    private Pane saveLoginPane;

    @FXML
    private void onLoginMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
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
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void OnForgotPasswordMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            saveLoginPane = (Pane) rightFormStackPane.getChildren().removeLast();
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
