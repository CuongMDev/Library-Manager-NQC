package com.example.librarymanagernqc.Login;

import com.example.librarymanagernqc.Objects.AccountChecker.AccountChecker;
import com.example.librarymanagernqc.database.Controller.AdminDatabaseController;
import com.example.librarymanagernqc.database.DAO.AdminDAO;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewPassword {
    @FXML
    public Button backToLoginButton;
    @FXML
    private Text errorText;
    @FXML
    private TextField newPasswordField1;
    @FXML
    private TextField newPasswordField2;
    @FXML
    public JFXButton backButton;

    private String username;

    @FXML
    private void onSetMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (AccountChecker.checkMatchPassword(newPasswordField1.getText(), newPasswordField2.getText())) {
                try {
                    AdminDatabaseController.setPassword(username, newPasswordField1.getText());
                } catch (Exception e) {
                    errorText.setText("Please recheck your Internet Connection");
                    e.printStackTrace();
                }

                //back to login
                backToLoginButton.fireEvent(mouseEvent);
            } else {
                errorText.setText(AccountChecker.getErrorMessage());
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
