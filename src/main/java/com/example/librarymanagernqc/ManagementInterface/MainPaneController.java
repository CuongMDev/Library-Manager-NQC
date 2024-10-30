package com.example.librarymanagernqc.ManagementInterface;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class MainPaneController {
    private enum PaneType {
        DOCUMENT
    }

    @FXML
    private JFXButton homeButton;
    @FXML
    private StackPane mainStackPane;

    private final Pane[] panes = new Pane[PaneType.values().length];

    @FXML
    private void initialize() throws IOException {
        panes[PaneType.DOCUMENT.ordinal()] = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Document/document.fxml")));
    }

    public void switchPane(PaneType paneType) {
        mainStackPane.getChildren().setAll(panes[paneType.ordinal()]);
    }

    @FXML
    void OnHomeButtonClicked() {
        switchPane(PaneType.DOCUMENT);
    }
}