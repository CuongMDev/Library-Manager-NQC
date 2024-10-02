package com.example.librarymanagernqc;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LibController {
    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Library Controller NQC Application!");
        checkBox.setSelected(true);
        checkBox.setDisable(true);
    }
}