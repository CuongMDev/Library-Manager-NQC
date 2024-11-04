package com.example.librarymanagernqc.ManagementInterface;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class  MainPaneController {
    private enum PaneType {
        DOCUMENT,
        USER,
        BORROW_LIST
    }

    @FXML
    private JFXButton homeButton;
    @FXML
    public JFXButton logoutButton;
    @FXML
    private StackPane mainStackPane;

    private final Pane[] panes = new Pane[PaneType.values().length];

    @FXML
    private void initialize() throws IOException {
        panes[PaneType.DOCUMENT.ordinal()] = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Document/document.fxml")));
        panes[PaneType.USER.ordinal()] = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("User/user.fxml")));
        panes[PaneType.BORROW_LIST.ordinal()] = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BorrowedList/borrowedList.fxml")));

    }

    public void switchPane(PaneType paneType) {
        mainStackPane.getChildren().setAll(panes[paneType.ordinal()]);
    }

    @FXML
    private void OnHomeButtonClicked() {
        switchPane(PaneType.DOCUMENT);
    }

    @FXML
    private void OnUserButtonClicked() {
        switchPane(PaneType.USER);
    }
}
