package com.example.librarymanagernqc.database;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.database.Controller.BookDatabaseController;
import com.example.librarymanagernqc.database.Controller.BorrowedListDatabaseController;
import com.example.librarymanagernqc.database.Controller.RecentBorrowedDatabaseController;
import com.example.librarymanagernqc.database.Controller.ReturnedListDatabaseController;
import com.example.librarymanagernqc.database.Controller.UserDatabaseController;

import java.util.List;

public class DatabaseLoader extends HasError {
    private static DatabaseLoader instance;

    // Private constructor to prevent instantiation
    private DatabaseLoader() {
    }

    public static DatabaseLoader getInstance() {
        if (instance == null) {
            instance = new DatabaseLoader();
        }
        return instance;
    }

    /**
     * Phương thức này được gọi để tải data từ database
     * @return true if load successfully, false if not
     */
    public boolean loadDataFromDatabase() {
        boolean success = true;
        if (!BookDatabaseController.getInstance().loadBooksFromDatabase()) {
            success = false;
        } else if (!UserDatabaseController.getInstance().loadMemberFromDatabase()) {
            success = false;
        } else if(!BorrowedListDatabaseController.getInstance().loadBookLoanFromDatabase()) {
            success = false;
        } else if(!ReturnedListDatabaseController.getInstance().loadBookReturnFromDatabase()) {
            success = false;
        } else if (!RecentBorrowedDatabaseController.getInstance().loadRecentBorrowedFromDatabase()) {
            success = false;
        }

        if (!success) {
            setErrorMessage("Error loading data from database");
        }
        return success;
    }
}
