package com.example.librarymanagernqc.database;

import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.Objects.Book.Book;

import java.util.List;

public class DatabaseLoader {
    // Private constructor to prevent instantiation
    private DatabaseLoader() {
    }

    private static String errorMessage = null;

    // Phương thức này được gọi để tải sách từ database
    private static boolean loadBooksFromDatabase() {
        BookDAO bookDAO = new BookDAO();
        try {
            List<Book> booksFromDb = bookDAO.getBooksFromDatabase();
            //tải sách vào database
            DocumentController.setAllBooksList(booksFromDb);
            return true;
        }
        catch (Exception e) {
            errorMessage = e.getMessage();
            return false;
        }
    }

    /**
     * Phương thức này được gọi để tải data từ database
     * @return true if load successfully, false if not
     */
    public static boolean loadDataFromDatabase() {
        if (!loadBooksFromDatabase()) {
            return false;
        }
        return true;
    }

    /**
     * @return latest error message, if not exist return null
     */
    public static String getErrorMessage() {
        return errorMessage;
    }
}
