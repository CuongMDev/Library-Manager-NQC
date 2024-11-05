package com.example.librarymanagernqc.Objects;

import javafx.scene.control.TextFormatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
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

    /**
     * search book by title, limit = 0 mean no limit
     */
    public static<T> List<T> fuzzySearch(List<T> list, String word, String methodName, int maxDistance, int limit) {
        if (limit == 0) {
            limit = list.size();
        }

        List<T> results = new LinkedList<>();
        for (T item : list) {
            // Lấy phương thức được chỉ định từ lớp T
            try {
                Method method = item.getClass().getMethod(methodName);
                String itemWord = (String) method.invoke(item);

                if (wordDistance(word, itemWord) <= maxDistance) {
                    results.add(item);
                    if (results.size() >= limit) {
                        break;
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private static int wordDistance(String findWord, String word) {
        for (int i = 0, j = 0; i < findWord.length(); i++) {
            while (j < word.length() && Character.toLowerCase(findWord.charAt(i)) != Character.toLowerCase(word.charAt(j))) {
                j++;
            }
            if (j == word.length()) {
                return findWord.length() - i;
            }
        }

        return 0;
    }
}
