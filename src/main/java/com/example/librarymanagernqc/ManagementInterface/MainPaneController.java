package com.example.librarymanagernqc.ManagementInterface;

import com.example.librarymanagernqc.ManagementInterface.BorrowedList.BorrowedListController;
import com.example.librarymanagernqc.ManagementInterface.ReturnedList.ReturnedListController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class  MainPaneController {
    private enum PaneType {
        DOCUMENT,
        USER,
        BORROWED_LIST,
        RETURNED_LIST
    }

    @FXML
    private JFXButton documentButton;
    @FXML
    public JFXButton logoutButton;
    @FXML
    private StackPane mainStackPane;

    private final FXMLLoader[] loaders = new FXMLLoader[PaneType.values().length];
    private final Pane[] panes = new Pane[PaneType.values().length];

    @FXML
    private void initialize() throws IOException {
        loaders[PaneType.DOCUMENT.ordinal()] = new FXMLLoader(getClass().getResource("Document/document.fxml"));
        loaders[PaneType.USER.ordinal()] = new FXMLLoader(getClass().getResource("User/user.fxml"));
        loaders[PaneType.BORROWED_LIST.ordinal()] = new FXMLLoader(getClass().getResource("BorrowedList/borrowed-list.fxml"));
        loaders[PaneType.RETURNED_LIST.ordinal()] = new FXMLLoader(getClass().getResource("ReturnedList/returned-list.fxml"));

        for (int i = 0; i < PaneType.values().length; i++) {
            panes[i] = loaders[i].load();
        }
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

            BorrowedListController borrowedListController = loaders[PaneType.BORROWED_LIST.ordinal()].getController();
            borrowedListController.updateTable();
        }
    }

    @FXML
    public void OnReturnedButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.RETURNED_LIST);

            ReturnedListController returnedListController = loaders[PaneType.RETURNED_LIST.ordinal()].getController();
            returnedListController.updateTable();
        }
    }
}
