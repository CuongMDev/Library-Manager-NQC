package com.example.librarymanagernqc.database.DAO;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.database.DatabaseHelper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecentBorrowedDAO {

  //lấy recent borrowed từ database
  public List<Book> getRecentBorrowedFromDatabase() throws SQLException {
    List<Book> recentBooks = new ArrayList<>();

    String query = "SELECT * FROM RecentBorrowed";

    try(Statement statement = DatabaseHelper.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query)){
      while (resultSet.next()) {
        String bookId = resultSet.getString("book_id");
        String title = resultSet.getString("title");
        int quantity = resultSet.getInt("quantity");
        String authorName = resultSet.getString("author_name");
        String description = resultSet.getString("description");
        String publisher = resultSet.getString("publisher");
        String publishedDate = resultSet.getString("published_date");
        String thumbnailUrl = resultSet.getString("thumbnailUrl");
        String infoLink = resultSet.getString("infoLink");

        Book book = new Book(bookId, title, authorName, publisher, publishedDate, description,
            quantity, thumbnailUrl, infoLink);

        recentBooks.add(book);
      }
    } catch (SQLException e){
      e.printStackTrace();
      throw new SQLException(e);
    }

    return recentBooks;
  }

  //insert new borrowed in database
  public boolean insertRecentBorrowed(Book book) {
    String query = "INSERT INTO RecentBorrowed (book_id, title, quantity, author_name, description, publisher, published_date, thumbnailUrl, infoLink) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement statement = DatabaseHelper.getConnection().prepareStatement(query)) {

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
          java.sql.Date sqlDate = java.sql.Date.valueOf(
              publishedDate); // Ngày phải có định dạng "yyyy-MM-dd"
          statement.setDate(7, sqlDate);
        } catch (IllegalArgumentException e) {
          // Xử lý trường hợp ngày không hợp lệ
          System.out.println("Ngày không hợp lệ: " + publishedDate);
          statement.setNull(7, java.sql.Types.DATE); // Nếu ngày không hợp lệ, set NULL
        }
      } else {
        statement.setNull(7, java.sql.Types.DATE); // Nếu ngày là null hoặc rỗng, set NULL
      }
      statement.setString(8, book.getThumbnailUrl());
      statement.setString(9, book.getInfoLink());

      int rowsInserted = statement.executeUpdate();
      return rowsInserted > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // kiểm tra book_id đã tồn tại trong book recent database chưa
  public boolean isRecentBookExists(String bookId) {
    String sql = "SELECT COUNT(*) FROM RecentBorrowed WHERE book_id = ?";
    try (PreparedStatement statement = DatabaseHelper.getConnection().prepareStatement(sql)) {
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
}
