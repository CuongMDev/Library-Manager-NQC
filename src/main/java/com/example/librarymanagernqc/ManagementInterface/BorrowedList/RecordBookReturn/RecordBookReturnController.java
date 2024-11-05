package com.example.librarymanagernqc.ManagementInterface.BorrowedList.RecordBookReturn;

import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RecordBookReturnController {
    @FXML
    private TextField username;
    @FXML
    private TextField bookTitle;
    @FXML
    private TextField status;
    @FXML
    private TextField daysOverdue;
    @FXML
    private TextField fine;
    @FXML
    private DatePicker loanDate;
    @FXML
    private DatePicker dueDate;
    @FXML
    private ComboBox<String> bookCondition;
    @FXML
    public JFXButton recordButton;
    @FXML
    public JFXButton cancelButton;

    @FXML
    private void initialize() {
        bookCondition.getItems().addAll("100%", "90%", "80%", "70%", "60%", "50%", "40%", "30%", "20%", "10%");

        username.setEditable(false);
        bookTitle.setEditable(false);
        status.setEditable(false);
        daysOverdue.setEditable(true);
        fine.setEditable(false);
        loanDate.setEditable(true);
        dueDate.setEditable(true);
    }

    public void addBook(BookLoan bookLoan) {
        username.setText(bookLoan.getUsername());
        bookTitle.setText(bookLoan.getBookTitle());
        status.setText(bookLoan.getStatus());
        loanDate.getValue();
        dueDate.getValue();
        bookCondition.getItems().clear();
        daysOverdue.setText("####");
    }
}
