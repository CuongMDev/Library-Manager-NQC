package com.example.librarymanagernqc.database;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.ManagementInterface.Document.DocumentController;
import com.example.librarymanagernqc.ManagementInterface.User.UserController;
import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.database.Controller.BookDatabaseController;
import com.example.librarymanagernqc.database.Controller.UserDatabaseController;

import java.util.List;

public class DatabaseLoader extends HasError {
    // Private constructor to prevent instantiation
    private DatabaseLoader() {
    }

    /**
     * Phương thức này được gọi để tải data từ database
     * @return true if load successfully, false if not
     */
    public static boolean loadDataFromDatabase() {
        boolean success = true;
        if (!BookDatabaseController.loadBooksFromDatabase()) {
            success = false;
        } else if (!UserDatabaseController.loadMemberFromDatabase()) {
            success = false;
        }

        if (!success) {
            setErrorMessage("Error loading data from database");
        }
        return success;
    }
}