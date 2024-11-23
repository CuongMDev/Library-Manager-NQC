package com.example.librarymanagernqc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
  private static final String DB_URL = "jdbc:mysql://localhost/libraries";  // Đảm bảo thay đổi đúng URL và port của bạn
  private static final String DB_USER = "NQC";  // Thay đổi nếu cần
  private static final String DB_PASSWORD = "NQC";  // Thay đổi mật khẩu nếu cần

  // Biến static duy nhất cho kết nối
  private static Connection connection;

  // Hàm getConnection sẽ kiểm tra nếu kết nối đã tồn tại, nếu chưa thì tạo mới
  public static Connection getConnection() throws SQLException {
    // Kiểm tra xem kết nối đã được tạo chưa
    if (connection == null || connection.isClosed()) {
      try {
        // Tạo kết nối mới nếu chưa có hoặc đã đóng
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      } catch (SQLException e) {
        throw new SQLException("Error establishing database connection", e);
      }
    }
    return connection;
  }

  // Đảm bảo đóng kết nối khi ứng dụng kết thúc
  public static void closeConnection() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

