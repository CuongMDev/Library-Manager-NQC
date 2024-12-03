package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.Book.BookInfo;
import com.example.librarymanagernqc.database.DAO.RecentBorrowedDAO;

import java.lang.ModuleLayer.Controller;
import java.util.Collections;
import java.util.List;

public class RecentBorrowedDatabaseController extends HasError {
    private static final RecentBorrowedDAO recentBorrowedDAO = new RecentBorrowedDAO();

    private static RecentBorrowedDatabaseController instance;

    // Private constructor to prevent instantiation
    private RecentBorrowedDatabaseController() {
    }

    public static RecentBorrowedDatabaseController getInstance() {
        if (instance == null) {
            instance = new RecentBorrowedDatabaseController();
        }
        return instance;
    }

    // tải recent book từ database
    public boolean loadRecentBorrowedFromDatabase() {
        try {
            List<BookInfo> recentBorrowedFromDb = recentBorrowedDAO.getRecentBorrowedFromDatabase();
            DocumentController.setAllRecentList(recentBorrowedFromDb.reversed());
            return true;
        } catch (Exception e) {
            setErrorMessage("Error loading Recent Borrowed from database");
            return false;
        }
    }

    //thêm recent borrowed
    public boolean insertRecentBorrowed(Book newBook) {
        if (recentBorrowedDAO.insertRecentBorrowed(newBook)) {
            return true;
        }
        setErrorMessage("Error inserting Recent Borrowed to Database");
        return false;
    }

    public boolean isRecentBookExists(String bookId) {
        if (recentBorrowedDAO.isRecentBookExists(bookId)) {
            setErrorMessage("Book exist");
            return true;
        }
        return false;
    }
}
