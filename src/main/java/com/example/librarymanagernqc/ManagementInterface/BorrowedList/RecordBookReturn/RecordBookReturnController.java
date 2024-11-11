package com.example.librarymanagernqc.ManagementInterface.BorrowedList.RecordBookReturn;

import com.example.librarymanagernqc.ManagementInterface.Document.BookInformation.BookInformationController;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.Objects.Utils;
import com.example.librarymanagernqc.ManagementInterface.ReturnedList.ReturnedListController;
import com.example.librarymanagernqc.TimeGetter.TimeGetter;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class RecordBookReturnController {
    public enum Type {
        RECORD,
        INFOMATION
    }

    private Type currentType;

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
    private TextField loanDate;
    @FXML
    private TextField dueDate;
    @FXML
    private ComboBox<String> bookCondition;
    @FXML
    public JFXButton recordButton;
    @FXML
    public JFXButton cancelButton;
    @FXML
    public JFXButton backButton;

    private void setFieldsProperties() {
        username.setEditable(false);
        bookTitle.setEditable(false);
        status.setEditable(false);
        daysOverdue.setEditable(false);
        loanDate.setEditable(false);
        dueDate.setEditable(false);
        backButton.setVisible(false);

        //default;
        currentType = Type.RECORD;

        //chỉ cho phép nhập số
        fine.setTextFormatter(new TextFormatter<>(Utils.numberFilter));
    }

    private void setInitValue() {
        bookCondition.getItems().addAll("100%", "90%", "80%", "70%", "60%", "50%", "40%", "30%", "20%", "10%");
        bookCondition.setValue("100%");
    }

    @FXML
    private void initialize() {
        setFieldsProperties();
        setInitValue();
    }

    public void setType(RecordBookReturnController.Type type) {
        if (type == Type.INFOMATION) {
            cancelButton.setVisible(false);
            recordButton.setVisible(false);
            backButton.setVisible(true);

            //không cho sửa
            bookCondition.getItems().setAll(bookCondition.getValue());
            fine.setEditable(false);
        }
    }

    public void setBookLoan(BookLoan bookLoan) {
        username.setText(bookLoan.getUsername());
        bookTitle.setText(bookLoan.getBookTitle());
        status.setText(bookLoan.getStatus());
        loanDate.setText(bookLoan.getLoanDate());
        dueDate.setText(bookLoan.getDueDate());
        daysOverdue.setText(String.valueOf(Math.max(0, ChronoUnit.DAYS.between(LocalDate.parse(bookLoan.getDueDate(), Utils.isoFormatter), TimeGetter.getCurrentTime().toLocalDate()))));
        if (currentType == Type.INFOMATION) {
            //không cho sửa
            bookCondition.getItems().setAll(bookLoan.getBookCondition());
        }
        bookCondition.setValue(bookLoan.getBookCondition());
        fine.setText(bookLoan.getFine());
    }

    public void updateBookLoan(BookLoan bookLoan) {
        bookLoan.setBookCondition(bookCondition.getValue());
        bookLoan.setFine(fine.getText());
    }

}
