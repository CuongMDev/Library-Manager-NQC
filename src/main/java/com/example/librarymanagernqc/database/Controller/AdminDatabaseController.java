package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.database.DAO.AdminDAO;

public class AdminDatabaseController extends HasError {

  private static AdminDAO adminDAO = new AdminDAO();

  // Private constructor to prevent instantiation
  private AdminDatabaseController() {
  }

  public static boolean checkValidAccount(String username, String password) {
    if (adminDAO.isAcountExists(username, password)) {
      return true;
    }
    setErrorMessage("Invalid username or password");
    return false;
  }

  public static boolean checkValidUsername(String username) {
    if(adminDAO.isUserExists(username)){
      return true;
    }
    setErrorMessage("Username not exist");
    return false;
  }

  public static boolean checkValidKey(String username, String key){
    if(adminDAO.isKeyExists(username, key)){
      return true;
    }
    setErrorMessage("Invalid key");
    return false;
  }

  public static void setPassword(String username, String password){
    adminDAO.setPassword(username, password);
  }

  public static void setRecoveryKey(String username, String key){
    if(adminDAO.setRecoveryKey(username, key)){
      System.out.println("Successfully set recovery key");
    }
  }
}
