package com.example.librarymanagernqc.ManagementInterface.User.AddUser;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class AddUserController {
    @FXML
    public JFXButton cancelButton;
    @FXML
    private ComboBox sexBox;

    @FXML
    private void initialize() {
        // Thêm lựa chọn vào ComboBox
        sexBox.getItems().addAll("Nam", "Nữ", "Chưa biết");
    }
}
