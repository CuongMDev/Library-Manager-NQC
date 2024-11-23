package com.example.librarymanagernqc.database.DAO;

import com.example.librarymanagernqc.database.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
  private Connection connection;

  // Khởi tạo kết nối một lần duy nhất từ DatabaseHelper
  public AdminDAO() {
    try {
      this.connection = DatabaseHelper.getConnection(); // Lấy kết nối từ DatabaseHelper
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private Connection getValidConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        connection = DatabaseHelper.getConnection();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  // Đảm bảo kết nối được đóng khi không còn sử dụng
  public void closeConnection() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close(); // Đóng kết nối
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  // kiểm tra thông tin đăng nhập có đúng không
  public boolean isAcountExists(String username, String password) {
    String sql = "SELECT COUNT(*) FROM Admins WHERE admin_user_name = ? AND password = ?";
    try (PreparedStatement statement = getValidConnection().prepareStatement(sql)) {
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
