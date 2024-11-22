package com.example.librarymanagernqc.database.DAO;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.database.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BorrowedListDAO {

  private Connection connection;

  // Khởi tạo kết nối một lần duy nhất từ DatabaseHelper
  public BorrowedListDAO() {
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

  public List<BookLoan> getBookLoanFromDatabase() throws SQLException {
    List<BookLoan> bookLoanList = new ArrayList<BookLoan>();
    String query = "SELECT * FROM BorrowedList";

    try (Statement statement = getValidConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {

      while (resultSet.next()) {
        // Lấy dữ liệu từ ResultSet và tạo đối tượng BookLoan
        int loan_id = resultSet.getInt("loan_id");
        String username = resultSet.getString("member_name");
        String bookId = resultSet.getString("book_id");
        String bookTitle = resultSet.getString("bookTitle");
        String loanDate = resultSet.getString("loan_date");
        String dueDate = resultSet.getString("due_date");
        int loanQuantity = resultSet.getInt("loanQuantity");

        BookLoan bookloan = new BookLoan(loan_id, username, bookTitle, bookId, loanDate, dueDate,
            loanQuantity);

        bookLoanList.add(bookloan);  // Thêm vào danh sách
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }
    return bookLoanList;
  }

  // thêm sách mượn vào cơ sở dữ liệu
  public boolean insertBookLoan(BookLoan bookLoan) {
    String query = "INSERT INTO BorrowedList (loan_id, member_name, book_id, loan_date, due_date, loanQuantity, status, fine, bookCondition, bookTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement statement = getValidConnection().prepareStatement(query)) {

      statement.setInt(1, bookLoan.getLoanId());
      statement.setString(2, bookLoan.getUsername());
      statement.setString(3, bookLoan.getBookId());

      String loanDate = bookLoan.getLoanDate();
      if (loanDate != null && !loanDate.isEmpty()) {
        statement.setDate(4, java.sql.Date.valueOf(loanDate));
      } else {
        statement.setNull(4, java.sql.Types.DATE);
      }

      String dueDate = bookLoan.getDueDate();
      if (dueDate != null && !dueDate.isEmpty()) {
        statement.setDate(5, java.sql.Date.valueOf(dueDate));
      } else {
        statement.setNull(5, java.sql.Types.DATE);
      }
      statement.setInt(6, bookLoan.getLoanQuantity());
      statement.setString(7, bookLoan.getStatus());
      statement.setString(8, bookLoan.getFine());
      statement.setString(9, bookLoan.getBookCondition());
      statement.setString(10, bookLoan.getBookTitle());

      int rowsInserted = statement.executeUpdate();
      return rowsInserted > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // xóa thông tin mượn sách khỏi database
  public boolean deleteBookLoanById(BookLoan bookLoan) {
    String deleteQuery = "DELETE FROM BorrowedList WHERE loan_id = ?";

    try (PreparedStatement statement = getValidConnection().prepareStatement(deleteQuery)) {

      statement.setInt(1, bookLoan.getLoanId());

      int affectedRows = statement.executeUpdate();

      // Trả về true nếu có dòng bị ảnh hưởng (xóa thành công)
      return affectedRows > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
