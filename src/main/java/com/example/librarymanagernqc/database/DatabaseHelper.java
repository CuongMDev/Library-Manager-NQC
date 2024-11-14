package com.example.librarymanagernqc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
  private static final String DB_URL = "jdbc:mysql://localhost/libraries";  // Đảm bảo thay đổi đúng URL và port của bạn
  private static final String DB_USER = "NQC";  // Thay đổi nếu cần
  private static final String DB_PASSWORD = "NQC";  // Thay đổi mật khẩu nếu cần

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
  }
}
