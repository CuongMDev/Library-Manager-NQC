package com.example.librarymanagernqc.Objects.AccountChecker;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.database.DAO.AdminDAO;

public class AccountChecker extends HasError {
    // Private constructor to prevent instantiation
    private AccountChecker() {
    }

    public static boolean checkValidUsername(String username) {
        AdminDAO adminDAO = new AdminDAO();
        try {
            if (adminDAO.isUserExists(username)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection");
            e.printStackTrace();
            return false;
        }

        setErrorMessage("Username not exist");
        return false;
    }

    public static boolean checkValidAccount(String username, String password) {
        AdminDAO adminDAO = new AdminDAO();
        try {
            if (adminDAO.isAcountExists(username, password)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection");
            e.printStackTrace();
            return false;
        }
        
        setErrorMessage("Invalid username or password");
        return false;
    }

    public static boolean checkValidKey(String username, String key) {
        AdminDAO adminDAO = new AdminDAO();
        try {
            if (adminDAO.isKeyExists(username, key)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection");
            e.printStackTrace();
            return false;
        }

        setErrorMessage("Invalid key");
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
