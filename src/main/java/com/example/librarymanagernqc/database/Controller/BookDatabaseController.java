package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.database.DAO.BookDAO;

import java.util.List;

public class BookDatabaseController extends HasError {
    private static final BookDAO bookDAO = new BookDAO();

    // Private constructor to prevent instantiation
    private BookDatabaseController() {
    }

    // Phương thức này được gọi để tải sách từ database
    public static boolean loadBooksFromDatabase() {
        try {
            List<Book> booksFromDb = bookDAO.getBooksFromDatabase();
            //tải sách vào database
            DocumentController.setAllBooksList(booksFromDb);
            return true;
        }
        catch (Exception e) {
            setErrorMessage("Error loading Books from database");
            return false;
        }
    }

    public static boolean deleteBookById(String bookId) {
        if (!bookDAO.deleteBookById(bookId)) {
            setErrorMessage("Error deleting book");
            return false;
        }
        return true;
    }
}
