package com.example.librarymanagernqc.ManagementInterface;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainPane {
    @FXML
    JFXButton homeButton;
    @FXML
    StackPane mainStackPane;

    @FXML
    void OnHomeButtonClicked() {
        switchToPane("Document/document.fxml");
    }

    // Phương thức để chuyển đổi các Pane bằng cách tải từ FXML
    private void switchToPane(String fxmlFile) {
        try {
            // Load FXML
            Parent scene = FXMLLoader.load(getClass().getResource(fxmlFile));
            mainStackPane.getChildren().setAll(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
