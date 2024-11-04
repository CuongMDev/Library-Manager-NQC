package com.example.librarymanagernqc;

import com.example.librarymanagernqc.ManagementInterface.MainPaneController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private JFXButton loginButton;
    @FXML
    private TextField usernameField;

    @FXML
    private void onLoginButtonClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            try {
                // Get the current stage
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();


                // Load the new scene
                FXMLLoader mainPaneLoader = new FXMLLoader(getClass().getResource("ManagementInterface/main-pane.fxml"));
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
                            stage.setScene(new Scene(new FXMLLoader(getClass().getResource("Login/login.fxml")).load()));
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
}
