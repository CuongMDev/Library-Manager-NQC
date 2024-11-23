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

public class ReturnedListDAO {
  public List<BookLoan> getBookReturnFromDatabase() throws SQLException {
    List<BookLoan> bookReturnList = new ArrayList<BookLoan>();
    String query = "SELECT * FROM ReturnList";

    try (Connection connection = DatabaseHelper.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {

      while (resultSet.next()) {
        // Lấy dữ liệu từ ResultSet và tạo đối tượng BookLoan
        String username = resultSet.getString("member_name");
        String bookId = resultSet.getString("book_id");
        String bookTitle = resultSet.getString("bookTitle");
        String loanDate = resultSet.getString("loan_date");
        String dueDate = resultSet.getString("due_date");
        int loanQuantity = resultSet.getInt("loanQuantity");
        String fine = resultSet.getString("fine");

        BookLoan bookloan = new BookLoan(username,bookTitle,bookId,loanDate,dueDate,loanQuantity, fine);

        bookReturnList.add(bookloan);  // Thêm vào danh sách
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SQLException(e);
    }
    return bookReturnList;
  }

  // thêm sách trả vào cơ sở dữ liệu
  public boolean insertBookReturn(BookLoan bookLoan) {
    String query = "INSERT INTO ReturnList (member_name, book_id, loan_date, due_date, loanQuantity, status, fine, bookCondition, bookTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseHelper.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, bookLoan.getUsername());
      statement.setString(2, bookLoan.getBookId());

      String loanDate = bookLoan.getLoanDate();
      if(loanDate != null && !loanDate.isEmpty()) {
        statement.setDate(3, java.sql.Date.valueOf(loanDate));
      } else{
        statement.setNull(3, java.sql.Types.DATE);
      }

      String dueDate = bookLoan.getDueDate();
      if(dueDate != null && !dueDate.isEmpty()) {
        statement.setDate(4, java.sql.Date.valueOf(dueDate));
      } else{
        statement.setNull(4, java.sql.Types.DATE);
      }
      statement.setInt(5, bookLoan.getLoanQuantity());
      statement.setString(6, bookLoan.getStatus());
      statement.setString(7, bookLoan.getFine());
      statement.setString(8, bookLoan.getBookCondition());
      statement.setString(9, bookLoan.getBookTitle());

      int rowsInserted = statement.executeUpdate();
      return rowsInserted > 0;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
