package com.example.librarymanagernqc.ManagementInterface;

import com.example.librarymanagernqc.ManagementInterface.BorrowedList.BorrowedListController;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.ManagementInterface.OverdueList.OverdueListController;
import com.example.librarymanagernqc.ManagementInterface.ReturnedList.ReturnedListController;
import com.example.librarymanagernqc.ManagementInterface.User.UserController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainPaneController {
    private enum PaneType {
        DOCUMENT,
        USER,
        BORROWED_LIST,
        RETURNED_LIST,
        OVERDUE_LIST
    }

    @FXML
    private JFXButton documentButton;
    @FXML
    private JFXButton userButton;
    @FXML
    private JFXButton borrowedButton;
    @FXML
    private JFXButton bookReturnButton;
    @FXML
    private JFXButton overdueButton;
    @FXML
    public JFXButton logoutButton;
    public JFXButton[] allButtons;
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
        loaders[PaneType.OVERDUE_LIST.ordinal()] = new FXMLLoader(getClass().getResource("OverdueList/overdue-list.fxml"));

        allButtons = new JFXButton[]{documentButton, userButton, borrowedButton, bookReturnButton, overdueButton, logoutButton};

        for (int i = 0; i < PaneType.values().length; i++) {
            panes[i] = loaders[i].load();
        }
    }

    public void init() {
        OnDocumentButtonClicked(new MouseEvent(
                MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, // Tọa độ x, y
                MouseButton.PRIMARY, // Nút chuột
                1, // Số lần nhấn
                false, false, false, false, // Modifier keys (Shift, Ctrl, Alt, Meta)
                true, false, false, false, false, false, null
        ));
    }

    public void switchPane(PaneType paneType) {
        mainStackPane.getChildren().setAll(panes[paneType.ordinal()]);
        for (JFXButton button : allButtons) {
            button.getStyleClass().remove("onChoose");
        }
    }

    @FXML
    private void OnDocumentButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.DOCUMENT);

            DocumentController documentController = loaders[PaneType.DOCUMENT.ordinal()].getController();
            documentController.updateTable();

            if (!documentButton.getStyleClass().contains("onChoose")) {
                documentButton.getStyleClass().add("onChoose");
            }
        }
    }

    @FXML
    private void OnUserButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.USER);

            UserController userController = loaders[PaneType.USER.ordinal()].getController();
            userController.updateTable();

            if (!userButton.getStyleClass().contains("onChoose")) {
                userButton.getStyleClass().add("onChoose");
            }
        }
    }

    @FXML
    public void OnBorrowedButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.BORROWED_LIST);

            BorrowedListController borrowedListController = loaders[PaneType.BORROWED_LIST.ordinal()].getController();
            borrowedListController.updateTable();

            if (!borrowedButton.getStyleClass().contains("onChoose")) {
                borrowedButton.getStyleClass().add("onChoose");
            }
        }
    }

    @FXML
    public void OnReturnedButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.RETURNED_LIST);

            ReturnedListController returnedListController = loaders[PaneType.RETURNED_LIST.ordinal()].getController();
            returnedListController.updateTable();

            if (!bookReturnButton.getStyleClass().contains("onChoose")) {
                bookReturnButton.getStyleClass().add("onChoose");
            }
        }
    }

    @FXML
    public void OnOverdueButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            switchPane(PaneType.OVERDUE_LIST);

            OverdueListController overdueListController = loaders[PaneType.OVERDUE_LIST.ordinal()].getController();
            overdueListController.updateTable();

            if (!overdueButton.getStyleClass().contains("onChoose")) {
                overdueButton.getStyleClass().add("onChoose");
            }
        }
    }
}
