package com.example.librarymanagernqc.ManagementInterface.User.AddUser;

import com.example.librarymanagernqc.ManagementInterface.Document.BookInformation.BookInformationController;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.User.User;
import com.example.librarymanagernqc.Objects.Utils;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddUserController {
    public enum Type {
        ADD,
        EDIT
    }

    @FXML
    public JFXButton addButton;
    @FXML
    public JFXButton cancelButton;
    @FXML
    private TextField username;
    @FXML
    private TextField citizenId;
    @FXML
    private TextField fullName;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField phoneNumber;

    @FXML
    private void initialize() {
        // Thêm lựa chọn vào ComboBox
        gender.getItems().addAll("Male", "Female", "Other", "Duc Nhat");

        username.setTextFormatter(new TextFormatter<>(Utils.alphabetNumberFilter));
        citizenId.setTextFormatter(new TextFormatter<>(Utils.alphabetNumberFilter));

        //chỉ cho phép nhập số
        phoneNumber.setTextFormatter(new TextFormatter<>(Utils.numberFilter));
    }

    private boolean checkValidUser() {
        //check empty
        if (username.getText().isEmpty() || citizenId.getText().isEmpty()
                || fullName.getText().isEmpty() || dateOfBirth.getValue() == null
                || gender.getValue() == null || phoneNumber.getText().isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean checkValidAndHandleBook() {
        if (checkValidUser()) {
            return true;
        }

        if (username.getText().isEmpty()) {
            if (!username.getStyleClass().contains("invalid")) {
                username.getStyleClass().add("invalid");
            }
        } else {
            username.getStyleClass().remove("invalid");
        }
        if (citizenId.getText().isEmpty()) {
            if (!citizenId.getStyleClass().contains("invalid")) {
                citizenId.getStyleClass().add("invalid");
            }
        } else {
            citizenId.getStyleClass().remove("invalid");
        }
        if (fullName.getText().isEmpty()) {
            if (!fullName.getStyleClass().contains("invalid")) {
                fullName.getStyleClass().add("invalid");
            }
        } else {
            fullName.getStyleClass().remove("invalid");
        }
        if (dateOfBirth.getValue() == null) {
            if (!dateOfBirth.getStyleClass().contains("invalid")) {
                dateOfBirth.getStyleClass().add("invalid");
            }
        } else {
            dateOfBirth.getStyleClass().remove("invalid");
        }
        if (gender.getValue() == null) {
            if (!gender.getStyleClass().contains("invalid")) {
                gender.getStyleClass().add("invalid");
            }
        } else {
            gender.getStyleClass().remove("invalid");
        }
        if (phoneNumber.getText().isEmpty()) {
            if (!phoneNumber.getStyleClass().contains("invalid")) {
                phoneNumber.getStyleClass().add("invalid");
            }
        } else {
            phoneNumber.getStyleClass().remove("invalid");
        }

        return false;
    }

    public void setType(BookInformationController.Type type) {
        if (type == BookInformationController.Type.EDIT) {
            addButton.setText("Save");
        }
    }

    public void setUser(User user) {
        username.setText(user.getUsername());
        citizenId.setText(user.getCitizenId());
        fullName.setText(user.getFullName());
        gender.setValue(user.getGender());
        phoneNumber.setText(user.getPhoneNumber());
        dateOfBirth.setValue(LocalDate.parse(user.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    public User getUser() {
        return new User(username.getText(), fullName.getText(),
                citizenId.getText(), gender.getValue(),
                dateOfBirth.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                phoneNumber.getText());
    }

}
