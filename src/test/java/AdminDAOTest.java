import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.librarymanagernqc.database.DAO.AdminDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminDAOTest {
  private AdminDAO adminDAO;

  @BeforeEach
  public void setUp() throws Exception {
    adminDAO = new AdminDAO();
  }

  @Test
  public void testIsAcountExists(){
    String ExistsUserName = "nqc";
    String ExistsPassword = "nqcnqcnqc";

    String nonExistsUserName = "iuqtgik";
    String nonExistsPassword = "iuqtgirgsfk";

    assertTrue(adminDAO.isAcountExists(ExistsUserName, ExistsPassword), "sách tồn tại và kiểm tra đúng");

    assertFalse(adminDAO.isAcountExists(nonExistsUserName, nonExistsPassword), "sách không tồn tại và kiểm tra không đúng");
  }

  @Test
  public void testIsUserExists(){
    String ExistsUserName = "nqc";

    String nonExistsUserName = "iuqtgik";

    assertTrue(adminDAO.isUserExists(ExistsUserName), "sách tồn tại và kiểm tra đúng");

    assertFalse(adminDAO.isUserExists(nonExistsUserName), "sách không tồn tại và kiểm tra không đúng");
  }
}
