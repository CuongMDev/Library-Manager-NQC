import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.database.DAO.ReturnedListDAO;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnListDAOTest {
  private ReturnedListDAO returnedListDAO;

  @BeforeEach
  public void setUp() throws Exception {
    returnedListDAO = new ReturnedListDAO();
  }

  @Test
  public void testGetBookReturnFromDatabase(){
    try{
      List<BookLoan> bookReturnList = returnedListDAO.getBookReturnFromDatabase();
      assertNotNull(bookReturnList, "Danh sách sách không thể là null");
    } catch (SQLException e){
      fail("Lỗi khi lấy danh sách sách từ database: " + e.getMessage());
    }
  }
}
