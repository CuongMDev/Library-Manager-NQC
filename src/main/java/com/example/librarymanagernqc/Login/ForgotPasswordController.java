package com.example.librarymanagernqc.Login;

import com.example.librarymanagernqc.Objects.AccountChecker.AccountChecker;
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

public class ForgotPasswordController {
    @FXML
    public Button backToLoginButton;
    @FXML
    public Button recoverButton;
    @FXML
    private TextField usernameField;
    @FXML
    private Text errorText;
    @FXML
    private StackPane mainStackPane;

    public void onRecoverMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (AccountChecker.checkValidUsername(usernameField.getText())) { //account valid
                Pane saveLoginPane = (Pane) mainStackPane.getChildren().removeLast();
                FXMLLoader recoveryKey = new FXMLLoader(getClass().getResource("recovery-key.fxml"));
                try {
                    mainStackPane.getChildren().add(recoveryKey.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                RecoveryKeyController recoveryKeyController = recoveryKey.getController();
                //set username
                recoveryKeyController.setUsername(usernameField.getText());

                recoveryKeyController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                    if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                        mainStackPane.getChildren().removeLast();
                        mainStackPane.getChildren().add(saveLoginPane);

                        //click by left mouse
                        backToLoginButton.fireEvent(mouseEvent);
                    }
                });

                //back button
                recoveryKeyController.backButton.setOnMouseClicked(backToLoginMouseEvent -> {
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
}
