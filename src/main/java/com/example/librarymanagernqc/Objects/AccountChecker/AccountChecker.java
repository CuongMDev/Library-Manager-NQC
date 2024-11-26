package com.example.librarymanagernqc.Objects.AccountChecker;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.database.Controller.AdminDatabaseController;
import com.example.librarymanagernqc.database.DAO.AdminDAO;

public class AccountChecker extends HasError {
    // Private constructor to prevent instantiation
    private AccountChecker() {
    }

    public static boolean checkValidUsername(String username) {
        try {
            if (AdminDatabaseController.checkValidUsername(username)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean checkValidAccount(String username, String password) {
        try {
            if (AdminDatabaseController.checkValidAccount(username, password)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean checkValidKey(String username, String key) {
        try {
            if (AdminDatabaseController.checkValidKey(username, key)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean checkMatchPassword(String password1, String password2) {
        if (password1.isEmpty()) {
            setErrorMessage("Empty password");
            return false;
        }
        if (!password1.equals(password2)) {
            setErrorMessage("Passwords do not match");
            return false;
        }

        return true;
    }
}
