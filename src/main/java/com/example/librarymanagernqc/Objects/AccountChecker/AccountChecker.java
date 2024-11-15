package com.example.librarymanagernqc.Objects.AccountChecker;

import com.example.librarymanagernqc.AbstractClass.HasError;

public class AccountChecker extends HasError {
    private static String errorMessage = null;

    // Private constructor to prevent instantiation
    private AccountChecker() {
    }

    public static boolean checkValidAccount(String username, String password) {
        if (username.equals("nqc") && password.equals("nqc")) {
            return true;
        }

        setErrorMessage("Invalid username or password");
        return false;
    }
}
