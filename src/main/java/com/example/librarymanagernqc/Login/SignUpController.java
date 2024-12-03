package com.example.librarymanagernqc.Login;

import com.example.librarymanagernqc.AbstractClass.Controller;
import com.example.librarymanagernqc.Objects.AccountController.AccountController;
import com.example.librarymanagernqc.database.Controller.AdminDatabaseController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class SignUpController extends Controller {
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
            boolean checkUserNameExists = AccountController.getInstance().isUserExists(usernameField.getText());
            if (AccountController.getInstance().checkUsernameCondition(usernameField.getText()) && !checkUserNameExists) {
                RecoveryKeyController recoveryKeyController;
                try {
                    recoveryKeyController = (RecoveryKeyController) Controller.init(getStage(), getClass().getResource("recovery-key.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                switchPane(mainStackPane, recoveryKeyController.getParent());
                AccountController.getInstance().insertAdminAcount(usernameField.getText(), "00000000");

                recoveryKeyController.setUsername(usernameField.getText());
                recoveryKeyController.setType(RecoveryKeyController.Type.GIVE_KEY);
                recoveryKeyController.giveKey(usernameField.getText());

                recoveryKeyController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                    if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                        switchToSavePane(mainStackPane);
                        //back to login
                        backToLoginButton.fireEvent(mouseEvent);
                    }
                });

                recoveryKeyController.backButton.setOnMouseClicked(backMouseEvent -> {
                    if (backMouseEvent.getButton() == MouseButton.PRIMARY) {
                        switchToSavePane(mainStackPane);
                    }
                });
            } else {
                errorText.setText(AccountController.getInstance().getErrorMessage());
            }
        }
    }
}
