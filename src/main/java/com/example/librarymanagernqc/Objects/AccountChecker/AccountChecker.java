package com.example.librarymanagernqc.Objects.AccountChecker;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.database.DAO.AdminDAO;

public class AccountChecker extends HasError {
    private static String errorMessage = null;

    // Private constructor to prevent instantiation
    private AccountChecker() {
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
}
