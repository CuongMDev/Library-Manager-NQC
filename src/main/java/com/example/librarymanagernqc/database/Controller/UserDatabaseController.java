package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.ManagementInterface.User.UserController;
import com.example.librarymanagernqc.User.User;
import com.example.librarymanagernqc.database.DAO.UserDAO;

import java.util.List;

public class UserDatabaseController extends HasError {
    private static UserDAO userDAO = new UserDAO();

    private static UserDatabaseController instance;

    // Private constructor to prevent instantiation
    private UserDatabaseController() {
    }

    public static UserDatabaseController getInstance() {
        if (instance == null) {
            instance = new UserDatabaseController();
        }
        return instance;
    }

    // Phương thức tải member từ database
    public boolean loadMemberFromDatabase() {
        try {
            List<User> usersFromDb = userDAO.getUserFromDatabase();
            //tải sách vào database
            UserController.setAllUsersList(usersFromDb);
            return true;
        } catch (Exception e) {
            setErrorMessage("Error loading users from database");
            return false;
        }
    }

    public boolean deleteUser(User user) {
        if (!userDAO.deleteUser(user)) {
            setErrorMessage("Error deleting user");
            return false;
        }
        return true;
    }

    // update user
    public boolean updateUser(User user) {
        if (!userDAO.updateUser(user)) {
            setErrorMessage("Error updating user");
            return false;
        }
        return true;
    }

    //kiểm tra user đã tồn tại chưa
    public boolean isUserExists(String username, String citizenId) {
        if (!userDAO.isUserExists(username, citizenId)) {
            setErrorMessage("User not found");
            return false;
        }
        return true;
    }

    //add user
    public boolean addUser(User user) {
        if (!userDAO.addUser(user)) {
            setErrorMessage("Error adding user");
            return false;
        }
        return true;
    }
}
