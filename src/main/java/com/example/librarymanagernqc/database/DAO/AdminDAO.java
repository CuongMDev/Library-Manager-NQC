package com.example.librarymanagernqc.database.DAO;

import com.example.librarymanagernqc.database.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

  // kiểm tra thông tin đăng nhập có đúng không
  public boolean isAcountExists(String username, String password) {
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
}
