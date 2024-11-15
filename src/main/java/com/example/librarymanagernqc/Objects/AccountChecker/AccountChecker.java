package com.example.librarymanagernqc.Objects.AccountChecker;

public class AccountChecker {
    private static String errorMessage = null;

    // Private constructor to prevent instantiation
    private AccountChecker() {
    }

    public static boolean checkValidAccount(String username, String password) {
        if (username.equals("nqc") && password.equals("nqc")) {
            return true;
        }

        errorMessage = "Invalid username or password";
        return false;
    }

    /**
     * @return latest error message, if not exist return null
     */
    public static String getErrorMessage() {
        return errorMessage;
    }
}
