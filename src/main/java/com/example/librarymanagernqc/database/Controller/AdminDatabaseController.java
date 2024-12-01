package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.database.DAO.AdminDAO;

public class AdminDatabaseController extends HasError {

  private static AdminDAO adminDAO = new AdminDAO();

  protected AdminDatabaseController() {
  }

  public static boolean checkValidAccount(String username, String password) {
    try {
      if(adminDAO.isAcountExists(username, password)) {
        return true;
      }
    } catch (Exception e){
      setErrorMessage("Please recheck your Internet Connection!");
      e.printStackTrace();
      return false;
    }
    setErrorMessage("Invalid username or password");
    return false;
  }

  public static boolean checkValidUsername(String username) {
    try{
      if(adminDAO.isUserExists(username)) {
        return true;
      }
    } catch (Exception e){
      setErrorMessage("Please recheck your Internet Connection!");
      return false;
    }
    setErrorMessage("Username not exist");
    return false;
  }

  public static boolean insertAdminAcount(String username, String password) {
    if (adminDAO.insertAdminAcount(username, password)) {
      return true;
    }
    setErrorMessage("Username already exist");
    return false;
  }

  //sign up
  public static boolean isUserExists(String username) {
    if(adminDAO.isUserExists(username)){
      setErrorMessage("Username exist");
      return true;
    }
    return false;
  }

  public static boolean checkValidKey(String username, String key){
    try {
      if(adminDAO.isKeyExists(username, key)) {
        return true;
      }
    } catch (Exception e){
      setErrorMessage("Please recheck your Internet Connection!");
      e.printStackTrace();
      return false;
    }
    setErrorMessage("Invalid key");
    return false;
  }

  public static void setPassword(String username, String password){
    if(adminDAO.setPassword(username, password)){
      System.out.println("Successfully set password");
    }
    else{
      System.out.println("Failed to set password");
    }
  }

  public static void setRecoveryKey(String username, String key){
    if(adminDAO.setRecoveryKey(username, key)){
      System.out.println("Successfully set recovery key");
    }
  }
}
