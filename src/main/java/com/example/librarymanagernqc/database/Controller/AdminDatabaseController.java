package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.database.DAO.AdminDAO;

public abstract class AdminDatabaseController extends HasError {
    private final AdminDAO adminDAO = new AdminDAO();

    protected AdminDatabaseController() {
    }

    public boolean checkValidAccount(String username, String password) {
        try {
            if (adminDAO.isAcountExists(username, password)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection!");
            e.printStackTrace();
            return false;
        }
        setErrorMessage("Invalid username or password");
        return false;
    }

    public boolean checkValidUsername(String username) {
        try {
            if (adminDAO.isUserExists(username)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection!");
            return false;
        }

        setErrorMessage("Username not exist");
        return false;
    }

    public boolean insertAdminAcount(String username, String password) {
        try {
            if (adminDAO.insertAdminAcount(username, password)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection!");
            return false;
        }

        setErrorMessage("Username already exist");
        return false;
    }

    //sign up
    public boolean isUserExists(String username) {
        try {
            if (adminDAO.isUserExists(username)) {
                setErrorMessage("Username already exist");
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection!");
            return false;
        }
        return false;
    }

    public boolean checkValidKey(String username, String key) {
        try {
            if (adminDAO.isKeyExists(username, key)) {
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection!");
            e.printStackTrace();
            return false;
        }
        setErrorMessage("Invalid key");
        return false;
    }

    public boolean setPassword(String username, String password) {
        try {
            if (adminDAO.setPassword(username, password)) {
                System.out.println("Successfully set password");
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection!");
            e.printStackTrace();
            return false;
        }

        setErrorMessage("Failed to set password");
        System.out.println("Failed to set password");
        return false;
    }

    public boolean setRecoveryKey(String username, String key) {
        try {
            if (adminDAO.setRecoveryKey(username, key)) {
                System.out.println("Successfully set recovery key");
                return true;
            }
        } catch (Exception e) {
            setErrorMessage("Please recheck your Internet Connection!");
            return false;
        }

        setErrorMessage("Failed to set recovery key");
        System.out.println("Failed to set recovery key");
        return false;
    }
}
