package com.example.librarymanagernqc.ManagementInterface;

import com.example.librarymanagernqc.Book.Book;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class  MainPaneController {
    private enum PaneType {
        DOCUMENT,
        USER,
        BORROWED_LIST
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
        panes[PaneType.BORROWED_LIST.ordinal()] = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BorrowedList/borrowed-list.fxml")));

        //setDefault
        switchPane(PaneType.DOCUMENT);
    }

    public void switchPane(PaneType paneType) {
        mainStackPane.getChildren().setAll(panes[paneType.ordinal()]);
    }

    @FXML
    private void OnDocumentButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.DOCUMENT);
        }
    }

    @FXML
    private void OnUserButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.USER);
        }
    }

    @FXML
    public void OnBorrowedButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.BORROWED_LIST);
        }
    }
}
