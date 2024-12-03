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
import javafx.scene.text.Text;

public class NewPasswordController extends Controller {
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
    @FXML
    private Text title;

    private String username;

    @FXML
    private void onSetMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (AccountController.getInstance().checkMatchPassword(newPasswordField1.getText(), newPasswordField2.getText())) {
                if (AccountController.getInstance().checkValidPassword(newPasswordField1.getText())) {
                    try {
                        AccountController.getInstance().setPassword(username, newPasswordField1.getText());
                        System.out.println(username);
                    } catch (Exception e) {
                        errorText.setText("Please recheck your Internet Connection");
                        e.printStackTrace();
                    }

                    //back to login
                    backToLoginButton.fireEvent(mouseEvent);
                } else {
                    errorText.setText(AccountController.getInstance().getErrorMessage());
                }
            } else {
                errorText.setText(AccountController.getInstance().getErrorMessage());
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
