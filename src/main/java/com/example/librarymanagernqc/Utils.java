package com.example.librarymanagernqc;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class Utils {
    /**
    * Chỉ cho phép nhập số vào field
    */
    public static final UnaryOperator<TextFormatter.Change> numberFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d*")) { // Kiểm tra xem tất cả ký tự là số
            return change; // Cho phép thay đổi
        }
        return null; // Từ chối thay đổi
    };

    /**
    * chỉ cho phép nhập chữ cái
    */
    public static final UnaryOperator<TextFormatter.Change> alphabetFilter = change -> {
        String newText = change.getControlNewText();
        // Kiểm tra nếu văn bản mới chỉ chứa chữ cái
        if (newText.matches("[a-zA-Z]*")) {
            return change; // Cho phép thay đổi
        }
        return null; // Từ chối thay đổi
    };

    /**
    * chỉ cho phép nhập chữ cái hoặc số
    */
    public static final UnaryOperator<TextFormatter.Change> alphabetNumberFilter = change -> {
        String newText = change.getControlNewText();
        // Kiểm tra nếu văn bản mới chỉ chứa chữ cái hoặc số
        if (newText.matches("[a-zA-Z0-9]*")) {
            return change; // Cho phép thay đổi
        }
        return null; // Từ chối thay đổi
    };
}
