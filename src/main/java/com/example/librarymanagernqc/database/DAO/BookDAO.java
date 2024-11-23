package com.example.librarymanagernqc.database.DAO;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.database.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

  private Connection connection;

  // Khởi tạo kết nối một lần duy nhất từ DatabaseHelper
  public BookDAO() {
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

  public List<Book> getBooksFromDatabase() throws SQLException {
    List<Book> booksList = new ArrayList<>();

    String query = "SELECT * FROM Book";  // Truy vấn để lấy tất cả sách

    try (Statement statement = getValidConnection().createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

      while (resultSet.next()) {
        // Lấy dữ liệu từ ResultSet và tạo đối tượng Book
        String bookId = resultSet.getString("book_id");
        String title = resultSet.getString("title");
        int quantity = resultSet.getInt("quantity");
        String authorName = resultSet.getString("author_name");
        String description = resultSet.getString("description");
        String publisher = resultSet.getString("publisher");
//        Date publishedDate = resultSet.getDate("published_date");
        String publishedDate = resultSet.getString("published_date");

        Book book = new Book(bookId, title, authorName, publisher, publishedDate, description, quantity);

        booksList.add(book);  // Thêm vào danh sách sách
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }

    return booksList;
  }

  // xóa sách khỏi database
  public boolean deleteBookById(String bookId) {
    String deleteQuery = "DELETE FROM Book WHERE book_id = ?";

    try (PreparedStatement preparedStatement = getValidConnection().prepareStatement(deleteQuery)) {

      preparedStatement.setString(1, bookId);
      int affectedRows = preparedStatement.executeUpdate();

      // Trả về true nếu có dòng bị ảnh hưởng (xóa thành công)
      return affectedRows > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  // thêm sách vào cơ sở dữ liệu
  public boolean insertBook(Book book) {
    String sql = "INSERT INTO Book (book_id, title, quantity, author_name, description, publisher, published_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement statement = getValidConnection().prepareStatement(sql)) {

      statement.setString(1, book.getId());
      statement.setString(2, book.getTitle());
      statement.setInt(3, book.getQuantity());
      statement.setString(4, book.getAuthors());
      String description = book.getDescription();
      if (description.length() > 255) { // Giả sử giới hạn là 255 ký tự
        description = description.substring(0, 255);
      }
      statement.setString(5, description);
      statement.setString(6, book.getPublisher());
      String publishedDate = book.getPublishedDate();
      if (publishedDate != null && !publishedDate.isEmpty()) {
        try {
          // Kiểm tra xem chỉ có năm hay không
          if (publishedDate.length() == 4) { // Chỉ có năm
            publishedDate = publishedDate + "-01-01"; // Thêm ngày 1 tháng 1 vào
          }

          // Kiểm tra và chuyển đổi ngày từ chuỗi sang java.sql.Date
          java.sql.Date sqlDate = java.sql.Date.valueOf(publishedDate); // Ngày phải có định dạng "yyyy-MM-dd"
          statement.setDate(7, sqlDate);
        } catch (IllegalArgumentException e) {
          // Xử lý trường hợp ngày không hợp lệ
          System.out.println("Ngày không hợp lệ: " + publishedDate);
          statement.setNull(7, java.sql.Types.DATE); // Nếu ngày không hợp lệ, set NULL
        }
      } else {
        statement.setNull(7, java.sql.Types.DATE); // Nếu ngày là null hoặc rỗng, set NULL
      }

      int rowsInserted = statement.executeUpdate();
      return rowsInserted > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  // kiểm tra book_id đã tồn tại trong database chưa
  public boolean isBookExists(String bookId) {
    String sql = "SELECT COUNT(*) FROM Book WHERE book_id = ?";
    try (PreparedStatement statement = getValidConnection().prepareStatement(sql)) {
      statement.setString(1, bookId);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt(1) > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  // chỉnh sửa thông tin book
  public boolean updateBook(Book book) {
    String query = "UPDATE Book SET quantity = ? WHERE book_id = ?";
    try (PreparedStatement statement = getValidConnection().prepareStatement(query)){
      statement.setInt(1, book.getQuantity());
      statement.setString(2, book.getId());

      int rowsAffected = statement.executeUpdate();

      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // chỉnh sửa số lượng sách sau khi mượn hoặc trả
  public static boolean changeQuantityBook(int newQuantity, String bookId) {
    String query = "UPDATE Book SET quantity = ? WHERE book_id = ?";
    try (Connection connection = DatabaseHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)){
      statement.setInt(1, newQuantity);
      statement.setString(2, bookId);

      int rowsAffected = statement.executeUpdate();

      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}