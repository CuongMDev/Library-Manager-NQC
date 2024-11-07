package com.example.librarymanagernqc.ManagementInterface.User.RecordBookLoan;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.Objects.Utils;
import com.example.librarymanagernqc.TimeGetter.TimeGetter;
import com.example.librarymanagernqc.User.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class RecordBookLoanController {
    @FXML
    private TextField username;
    @FXML
    private TextField fullName;
    @FXML
    private TextField loanDate;
    @FXML
    private DatePicker dueDate;
    @FXML
    private TextField loanQuantity;

    @FXML
    private TextField bookTitle;
    @FXML
    private TextField bookId;
    @FXML
    private TextField bookAuthor;
    @FXML
    private TextField bookPublisher;
    @FXML
    private TextField bookQuantity;
    @FXML
    public JFXButton cancelButton;
    @FXML
    public JFXButton recordButton;
    @FXML
    private JFXListView<Book> suggestionTitleList;

    private void updateSuggestionTitleList() {
        String searchValue = bookTitle.getText();
        if (searchValue.isEmpty()) {
            suggestionTitleList.setVisible(false); // Ẩn bảng gợi ý khi TextField trống
        } else {
            // Lọc các gợi ý dựa trên nội dung đã nhập
            List<Book> filteredSuggestions = DocumentController.searchBooksList(searchValue, 5);
            // Cập nhật danh sách gợi ý
            suggestionTitleList.getItems().clear();
            for (Book book : filteredSuggestions) {
                suggestionTitleList.getItems().add(book);
            }
            suggestionTitleList.setVisible(!filteredSuggestions.isEmpty()); // Hiện bảng gợi ý nếu có gợi ý
        }
    }

    // Xử lý sự kiện chọn gợi ý
    @FXML
    private void onSuggestionMouseClicked(MouseEvent mouseEvent) {
        Book selected = suggestionTitleList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Điền gợi ý đã chọn vào TextField
            bookTitle.setText(selected.getTitle());
            bookId.setText(selected.getId());
            bookAuthor.setText(selected.getAuthors());
            bookPublisher.setText(selected.getPublisher());
            bookQuantity.setText(String.valueOf(selected.getQuantity()));

            suggestionTitleList.setVisible(false); // Ẩn bảng gợi ý sau khi chọn
        }
    }

    private void setFieldsProperties() {
        username.setEditable(false);
        fullName.setEditable(false);
        loanDate.setEditable(false);
        bookAuthor.setEditable(false);
        bookPublisher.setEditable(false);
        bookQuantity.setEditable(false);

        // Thiết lập bộ chuyển đổi để thay đổi định dạng hiển thị
        Utils.setConvertToMyFormatter(dueDate);
    }

    private void setInitValue() {
        loanDate.setText(TimeGetter.getCurrentTime().format(Utils.isoFormatter));
        loanQuantity.setText(String.valueOf(0));
    }

    //return true ID is found
    private boolean fillFieldsById() {
        Book findBook = DocumentController.searchBookById(bookId.getText());
        if (findBook != null) {
            // Điền gợi ý đã chọn vào TextField
            bookTitle.setText(findBook.getTitle());
            bookAuthor.setText(findBook.getAuthors());
            bookPublisher.setText(findBook.getPublisher());
            bookQuantity.setText(String.valueOf(findBook.getQuantity()));

            return true;
        }

        bookTitle.setText("");
        bookAuthor.setText("");
        bookPublisher.setText("");
        bookQuantity.setText("");

        return false;
    }

    @FXML
    private void initialize() {
        setFieldsProperties();
        setInitValue();

        // Lắng nghe thay đổi của title text
        bookTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            updateSuggestionTitleList();
        });
        // Lắng nghe thay đổi của id text
        bookId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (fillFieldsById()) { // Điền gợi ý đã chọn vào TextField
                suggestionTitleList.setVisible(false); //ẩn suggestion list
            }
        });

        // Hiển thị chỉ tiêu đề của sách
        suggestionTitleList.setCellFactory(lv -> new javafx.scene.control.ListCell<Book>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getTitle()); // Chỉ hiển thị tiêu đề
                }
            }
        });
    }

    public boolean checkValidBookLoan() {
        boolean valid = true;

        if (dueDate.getValue() == null) {// || dueDate.getValue().isBefore(LocalDate.parse(loanDate.getText(), DateTimeFormatter.ofPattern("M/d/yyyy")))) {
            valid = false;
            if (!dueDate.getStyleClass().contains("invalid")) {
                dueDate.getStyleClass().add("invalid");
            }
        } else {
            dueDate.getStyleClass().remove("invalid");
        }

        if (!fillFieldsById()) {
            valid = false;
            if (!bookTitle.getStyleClass().contains("invalid")) {
                bookTitle.getStyleClass().add("invalid");
            }
        } else {
            bookTitle.getStyleClass().remove("invalid");

            if (loanQuantity.getText().isBlank() || Integer.parseInt(loanQuantity.getText()) <= 0 || Integer.parseInt(loanQuantity.getText()) > Integer.parseInt(bookQuantity.getText())) {
                valid = false;
                if (!loanQuantity.getStyleClass().contains("invalid")) {
                    loanQuantity.getStyleClass().add("invalid");
                }
            } else {
                loanQuantity.getStyleClass().remove("invalid");
            }
        }

        return valid;
    }

    public void setUser(User user) {
        this.username.setText(user.getUsername());
        this.fullName.setText(user.getFullName());
    }

    public BookLoan getBookLoan() {
        return new BookLoan(username.getText(), bookTitle.getText(), bookId.getText(),
                loanDate.getText(),
                dueDate.getValue().format(Utils.isoFormatter),
                Integer.parseInt(loanQuantity.getText()));
    }
}
