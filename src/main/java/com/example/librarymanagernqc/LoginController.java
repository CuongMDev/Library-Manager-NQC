package com.example.librarymanagernqc;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {
    @FXML
    private JFXButton loginButton;
    @FXML
    private TextField usernameField;

    @FXML
    private void onLoginButtonClick() {
        try {
            // Load the new scene
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ManagementInterface/management-interface.fxml")));
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ManagementInterface/main-pane.fxml")));

            // Get the current stage
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            // Set the new scene
            stage.setScene(new Scene(scene));
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
