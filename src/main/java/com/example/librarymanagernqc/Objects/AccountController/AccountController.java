package com.example.librarymanagernqc.Objects.AccountController;

import com.example.librarymanagernqc.database.Controller.AdminDatabaseController;

public class AccountController extends AdminDatabaseController {
    private static AccountController instance;

    // Private constructor to prevent instantiation
    private AccountController() {
        super();
    }

    public static AccountController getInstance() {
        if (instance == null) {
            instance = new AccountController();
        }
        return instance;
    }

    public boolean checkUsernameCondition(String username) {
        if (username.isBlank()) {
            setErrorMessage("Name cannot be empty!");
            return false;
        }
        return true;
    }

    public boolean checkMatchPassword(String password1, String password2) {
        if (password1.isEmpty()) {
            setErrorMessage("Empty password");
            return false;
        }
        if (!password1.equals(password2)) {
            setErrorMessage("Passwords do not match!");
            return false;
        }

        return true;
    }

    public boolean checkValidPassword(String password) {
        if (password.isEmpty()) {
            setErrorMessage("Empty password!");
            return false;
        } else if (password.length() < 8 || password.length() > 20) {
            setErrorMessage("Password length should be between 8 and 20 characters!");
            return false;
        } else if (password.equals("00000000")) {
            setErrorMessage("Password should not be defaults password!");
            return false;
        }
        return true;
    }

    public boolean checkDefaultPassword(String password) {
        if (password.equals("00000000")) {
            setErrorMessage("Password should not be defaults password!");
            return true;
        }
        return false;
    }
}
