import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.database.DAO.BorrowedListDAO;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BorrowedListDAOTest {
  private BorrowedListDAO borrowedListDAO;

  @BeforeEach
  public void setUp() {
    borrowedListDAO = new BorrowedListDAO();
  }

  @Test
  public void testGetBookLoanFromDatabase() {
    try{
      List<BookLoan> bookLoanList = borrowedListDAO.getBookLoanFromDatabase();
      assertNotNull(bookLoanList, "Danh sách sách không thể là null");
    } catch (SQLException e){
      fail("Lỗi khi lấy danh sách sách từ database: " + e.getMessage());
    }
  }
}
