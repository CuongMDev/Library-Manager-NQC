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
import java.util.ArrayList;

public class RecoveryKeyController extends Controller {
    public enum Type {
        GIVE_KEY,
        SET_KEY
    }
    private Type currentType = Type.SET_KEY;
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
    @FXML
    private Text title;

    private String username;

    public void setType(Type type) {
        if (type == Type.GIVE_KEY) {
            recoverButton.setText("Confirm");
            title.setText("Recovery Key");
            currentType = Type.GIVE_KEY;

            key1.setEditable(false);
            key2.setEditable(false);
            key3.setEditable(false);
            key4.setEditable(false);
            key5.setEditable(false);
            key6.setEditable(false);
            key7.setEditable(false);
            key8.setEditable(false);
        } else {
            currentType = Type.SET_KEY;
        }
    }

    @FXML
    public void giveKey(String username) {
        ArrayList<String> keys = KeyController.getShuffleKeys();
        key1.setText(keys.get(0));
        key2.setText(keys.get(1));
        key3.setText(keys.get(2));
        key4.setText(keys.get(3));
        key5.setText(keys.get(4));
        key6.setText(keys.get(5));
        key7.setText(keys.get(6));
        key8.setText(keys.get(7));
        String key = String.format("%s-%s-%s-%s-%s-%s-%s-%s", key1.getText(), key2.getText(), key3.getText(), key4.getText(), key5.getText(), key6.getText(), key7.getText(), key8.getText());
        try{
            AccountController.getInstance().setRecoveryKey(username, key);
        } catch (Exception e){
            errorText.setText("unable to save key");
            e.printStackTrace();
        }
    }

    @FXML
    private void onRecoverMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            String key = String.format("%s-%s-%s-%s-%s-%s-%s-%s", key1.getText(), key2.getText(), key3.getText(), key4.getText(), key5.getText(), key6.getText(), key7.getText(), key8.getText());
            if (currentType == Type.GIVE_KEY || AccountController.getInstance().checkValidKey(username, key)) {
                NewPasswordController newPasswordController;
                try {
                    newPasswordController = (NewPasswordController) Controller.init(getStage(), getClass().getResource("new-password.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                switchPane(mainStackPane, newPasswordController.getParent());
                //set username
                newPasswordController.setUsername(username);
                newPasswordController.backToLoginButton.setOnMouseClicked(backToLoginMouseEvent -> {
                    if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                        switchToSavePane(mainStackPane);

                        //click by left mouse
                        backToLoginButton.fireEvent(mouseEvent);
                    }
                });

                //back button
                newPasswordController.backButton.setOnMouseClicked(backToLoginMouseEvent -> {
                    if (backToLoginMouseEvent.getButton() == MouseButton.PRIMARY) {
                        switchToSavePane(mainStackPane);
                    }
                });
            } else {
                errorText.setText(AccountController.getInstance().getErrorMessage());
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
