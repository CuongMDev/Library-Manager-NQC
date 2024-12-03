package com.example.librarymanagernqc.ManagementInterface;

import com.example.librarymanagernqc.AbstractClass.Controller;
import com.example.librarymanagernqc.ManagementInterface.BorrowedList.BorrowedListController;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.ManagementInterface.OverdueList.OverdueListController;
import com.example.librarymanagernqc.ManagementInterface.ReturnedList.ReturnedListController;
import com.example.librarymanagernqc.ManagementInterface.User.UserController;
import com.jfoenix.controls.JFXButton;
import eu.hansolo.tilesfx.addons.Switch;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainPaneController extends Controller {
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

    private final Controller[] controllers = new Controller[PaneType.values().length];

    @FXML
    private void initialize() throws IOException {
        controllers[PaneType.DOCUMENT.ordinal()] = Controller.init(getStage(), getClass().getResource("Document/document.fxml"));
        controllers[PaneType.USER.ordinal()] = Controller.init(getStage(), getClass().getResource("User/user.fxml"));
        controllers[PaneType.BORROWED_LIST.ordinal()] = Controller.init(getStage(), getClass().getResource("BorrowedList/borrowed-list.fxml"));
        controllers[PaneType.RETURNED_LIST.ordinal()] = Controller.init(getStage(), getClass().getResource("ReturnedList/returned-list.fxml"));
        controllers[PaneType.OVERDUE_LIST.ordinal()] = Controller.init(getStage(), getClass().getResource("OverdueList/overdue-list.fxml"));

        allButtons = new JFXButton[]{documentButton, userButton, borrowedButton, bookReturnButton, overdueButton, logoutButton};
        for (PaneType paneType : PaneType.values()) {
            JFXButton button = allButtons[paneType.ordinal()];
            button.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    switchMainPane(paneType);
                }
            });
        }
    }

    public void init() {
        switchMainPane(PaneType.DOCUMENT);
    }

    public void switchMainPane(PaneType paneType) {
        Controller controller = controllers[paneType.ordinal()];
        controller.refresh();
        switchPane(mainStackPane, controller.getParent());

        //unbold all button
        for (JFXButton button : allButtons) {
            button.getStyleClass().remove("onChoose");
        }

        //bold current button
        JFXButton button = allButtons[paneType.ordinal()];
        if (!button.getStyleClass().contains("onChoose")) {
            button.getStyleClass().add("onChoose");
        }
    }
}
