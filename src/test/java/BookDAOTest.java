import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.database.DAO.BookDAO;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookDAOTest {
  private BookDAO bookDAO;

  @BeforeEach
  public void setUp() throws Exception {
    bookDAO = new BookDAO();
  }

  @Test
  public void testGetBooksFromDatabase(){
    try {
      List<Book> books = bookDAO.getBooksFromDatabase();
      assertNotNull(books, "Danh sách sách không thể là null");
      assertTrue(books.size() > 0, "Danh sách sách không thể rỗng");
    } catch (SQLException e){
      fail("Lỗi khi lấy danh sách sách từ database: " + e.getMessage());
    }
  }

  @Test
  public void testIsBookExists() {
    String nonExistingBookId = "99999";
    assertFalse(bookDAO.isBookExists(nonExistingBookId), "Sách không tồn tại kiểm tra không đúng");
  }


}
