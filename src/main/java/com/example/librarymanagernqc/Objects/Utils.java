package com.example.librarymanagernqc.Objects;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.util.StringConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Utils {
    /**
     * Định nghĩa UnaryOperator để loại bỏ dấu cách
     */
    public static final UnaryOperator<TextFormatter.Change> noSpaceFilter = change -> {
        String newText = change.getText();
        // Nếu văn bản chứa dấu cách, bỏ qua thay đổi
        if (newText.contains(" ")) {
            return null;
        }
        return change; // Cho phép các thay đổi khác
    };

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

    public static final DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public static void setConvertToMyFormatter(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return Utils.isoFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, Utils.isoFormatter);
                } else {
                    return null;
                }
            }
        });
    }
}
