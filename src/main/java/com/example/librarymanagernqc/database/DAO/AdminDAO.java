package com.example.librarymanagernqc.database.DAO;

import com.example.librarymanagernqc.database.DatabaseHelper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

  // kiểm tra thông tin đăng nhập có đúng không
  public static boolean isAcountExists(String username, String password) {
    String sql = "SELECT COUNT(*) FROM Admins WHERE admin_user_name = ? AND password = ?";
    try (PreparedStatement statement = DatabaseHelper.getConnection().prepareStatement(sql)) {
      statement.setString(1, username);
      statement.setString(2, password);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt(1) > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  // kiểm tra username có tồn tại không
  public boolean isUserExists(String username) {
    String query = "SELECT COUNT(*) FROM Admins WHERE admin_user_name = ?";
    try (PreparedStatement statement = DatabaseHelper.getConnection().prepareStatement(query)) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt(1) > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }

  // kiểm tra key có đúng không
  public boolean isKeyExists(String username, String key) {
    String query = "SELECT COUNT(*) FROM Admins WHERE admin_user_name = ? AND check_key = ?";
    try (PreparedStatement statement = DatabaseHelper.getConnection().prepareStatement(query)) {
      statement.setString(1, username);
      statement.setString(2, key);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt(1) > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }

  //set password
  public boolean setPassword(String username, String password) {
    String query = "UPDATE Admins SET password = ? WHERE admin_user_name = ?";
    try (PreparedStatement statement = DatabaseHelper.getConnection().prepareStatement(query)) {
      statement.setString(1, password);
      statement.setString(2, username);

      int rowsAffected = statement.executeUpdate();

      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
