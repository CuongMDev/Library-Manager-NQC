package com.example.librarymanagernqc.ManagementInterface.User.AddUser;

import com.example.librarymanagernqc.AbstractClass.Controller;
import com.example.librarymanagernqc.ManagementInterface.Document.BookInformation.BookInformationController;
import com.example.librarymanagernqc.User.User;
import com.example.librarymanagernqc.Objects.Utils;
import com.example.librarymanagernqc.database.Controller.UserDatabaseController;
import com.example.librarymanagernqc.database.DAO.UserDAO;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.time.LocalDate;

public class AddUserController extends Controller {
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
    private void onAddButtonClicked() {
        if (checkValidUser()) {
            User user = getUser(); // Lấy thông tin người dùng từ các trường input

            //kiểm tra xem user đã có trong database chưa
            if(UserDatabaseController.getInstance().isUserExists(user.getUsername(), user.getCitizenId())) {
                System.out.println("User already exists");
                return;
            }

            if (UserDatabaseController.getInstance().addUser(user)) {
                System.out.println("Thêm người dùng vào database thành công");
                // Cập nhật giao diện nếu cần
            } else {
                System.out.println("Thêm người dùng vào database thất bại");
            }
        } else {
            System.out.println("Thông tin người dùng không hợp lệ");
        }
    }

    @FXML
    private void initialize() {
        // Thêm lựa chọn vào ComboBox
        gender.getItems().addAll("Male", "Female", "Other");

        username.setTextFormatter(new TextFormatter<>(Utils.noSpaceFilter));
        citizenId.setTextFormatter(new TextFormatter<>(Utils.alphabetNumberFilter));

        //chỉ cho phép nhập số
        phoneNumber.setTextFormatter(new TextFormatter<>(Utils.numberFilter));

        // Thiết lập bộ chuyển đổi để thay đổi định dạng hiển thị
        Utils.setConvertToMyFormatter(dateOfBirth);

        addButton.setOnAction(event -> onAddButtonClicked());
    }

    public boolean checkValidUser() {
        boolean valid = true;

        if (username.getText().isEmpty()) {
            if (!username.getStyleClass().contains("invalid")) {
                username.getStyleClass().add("invalid");
            }
            valid = false;
        } else {
            username.getStyleClass().remove("invalid");
        }

        if (citizenId.getText().isEmpty()) {
            if (!citizenId.getStyleClass().contains("invalid")) {
                citizenId.getStyleClass().add("invalid");
            }
            valid = false;
        } else {
            citizenId.getStyleClass().remove("invalid");
        }

        if (fullName.getText().isEmpty()) {
            if (!fullName.getStyleClass().contains("invalid")) {
                fullName.getStyleClass().add("invalid");
            }
            valid = false;
        } else {
            fullName.getStyleClass().remove("invalid");
        }

        if (dateOfBirth.getValue() == null) {
            if (!dateOfBirth.getStyleClass().contains("invalid")) {
                dateOfBirth.getStyleClass().add("invalid");
            }
            valid = false;
        } else {
            dateOfBirth.getStyleClass().remove("invalid");
        }

        if (gender.getValue() == null) {
            if (!gender.getStyleClass().contains("invalid")) {
                gender.getStyleClass().add("invalid");
            }
            valid = false;
        } else {
            gender.getStyleClass().remove("invalid");
        }

        if (phoneNumber.getText().isEmpty()) {
            if (!phoneNumber.getStyleClass().contains("invalid")) {
                phoneNumber.getStyleClass().add("invalid");
            }
            valid = false;
        } else {
            phoneNumber.getStyleClass().remove("invalid");
        }

        return valid;
    }

    public void setType(AddUserController.Type type) {
        if (type == AddUserController.Type.EDIT) {
            username.setEditable(false);
            citizenId.setEditable(false);
            addButton.setText("Save");
        }
    }

    public void setUser(User user) {
        username.setText(user.getUsername());
        citizenId.setText(user.getCitizenId());
        fullName.setText(user.getFullName());
        gender.setValue(user.getGender());
        phoneNumber.setText(user.getPhoneNumber());
        dateOfBirth.setValue(LocalDate.parse(user.getDateOfBirth(), Utils.isoFormatter));
    }

    public User getUser() {
        return new User(username.getText(), fullName.getText(),
                citizenId.getText(), gender.getValue(),
                dateOfBirth.getValue().toString(),
                phoneNumber.getText());
    }

}
