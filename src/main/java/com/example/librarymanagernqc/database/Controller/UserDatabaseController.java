package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.ManagementInterface.User.UserController;
import com.example.librarymanagernqc.User.User;
import com.example.librarymanagernqc.database.DAO.UserDAO;

import java.util.List;

public class UserDatabaseController extends HasError {
    private static UserDAO userDAO = new UserDAO();

    // Private constructor to prevent instantiation
    private UserDatabaseController() {
    }

    // Phương thức tải member từ database
    public static boolean loadMemberFromDatabase(){
        try {
            List<User> usersFromDb = userDAO.getUserFromDatabase();
            //tải sách vào database
            UserController.setAllUsersList(usersFromDb);
            return true;
        }
        catch (Exception e) {
            setErrorMessage("Error loading users from database");
            return false;
        }
    }

    public static boolean deleteUser(User user) {
        if (!userDAO.deleteUser(user)) {
            setErrorMessage("Error deleting user");
            return false;
        }
        return true;
    }
}
