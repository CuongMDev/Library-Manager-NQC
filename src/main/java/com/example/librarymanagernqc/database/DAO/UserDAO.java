package com.example.librarymanagernqc.database.DAO;

import com.example.librarymanagernqc.User.User;
import com.example.librarymanagernqc.database.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
  // lấy thông tin user từ database
  public List<User> getUserFromDatabase() throws SQLException {
    List<User> users = new ArrayList<User>();
    String query = "SELECT * FROM Member";
    try (Connection connection = DatabaseHelper.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

      while (resultSet.next()) {
        String username = resultSet.getString("member_name");
        String fullName = resultSet.getString("full_name");
        String citizenId = resultSet.getString("citizen_id");
        String gender = resultSet.getString("gender");
        String dateOfBirth = resultSet.getString("date_of_birth");
        String phoneNumber = resultSet.getString("phone_number");
        User user = new User(username, fullName, citizenId, gender, dateOfBirth, phoneNumber);
        users.add(user);
      }
    } catch (SQLException e){
      e.printStackTrace();
      throw new SQLException(e);
    }
    return users;
  }

  // thêm user vào database
  public boolean addUser(User user) {
    String insertUserSQL = "INSERT INTO Member (member_name, citizen_id, full_name, gender, phone_number, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseHelper.getConnection();
        PreparedStatement stmt = conn.prepareStatement(insertUserSQL)) {

      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getCitizenId());
      stmt.setString(3, user.getFullName());
      stmt.setString(4, user.getGender());
      stmt.setString(5, user.getPhoneNumber());
      stmt.setString(6, user.getDateOfBirth());

      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  //kiểm tra member_name đã tồn tại trong database chưa
  public boolean isUserExists(String username, String citizenId) {
    String query = "SELECT COUNT(*) FROM Member WHERE member_name = ? OR citizen_id = ?";
    try (Connection connection = DatabaseHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, username);  // Kiểm tra username
      statement.setString(2, citizenId); // Kiểm tra citizen_id
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return resultSet.getInt(1) > 0; // Nếu có bản ghi trùng, trả về true
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false; // Nếu không có bản ghi trùng, trả về false
  }

  // xóa user khỏi database
  public boolean deleteUser(User user) {
    String query = "DELETE FROM Member WHERE member_name = ?"; // Hoặc bạn có thể sử dụng citizenId

    try (Connection connection = DatabaseHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, user.getUsername()); // Hoặc sử dụng user.getCitizenId() nếu bạn xóa theo citizenId

      int rowsAffected = statement.executeUpdate();

      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false; // Nếu có lỗi, trả về false
    }
  }

    //chỉnh sửa thông tin user
  public boolean updateUser(User user){
    String query = "UPDATE Member SET full_name = ?, gender = ?, phone_number = ?, date_of_birth = ? WHERE member_name = ?";
    try (Connection connection = DatabaseHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)){
        statement.setString(1, user.getFullName());
        statement.setString(2, user.getGender());
        statement.setString(3, user.getPhoneNumber());
        statement.setString(4, user.getDateOfBirth());
        statement.setString(5, user.getUsername());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
