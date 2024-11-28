import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.example.librarymanagernqc.User.User;
import com.example.librarymanagernqc.database.DAO.UserDAO;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDAOTest {
  private UserDAO userDAO;

  @BeforeEach
  public void setUp() throws Exception {
    userDAO = new UserDAO();
  }

  @Test
  public void testUserFromDatabase(){
    try{
      List<User> users = userDAO.getUserFromDatabase();
      assertNotNull(users, "Danh sách không thể là null");
      assertTrue(users.size() > 0, "Danh sách không thể rỗng");
    } catch (SQLException e){
      fail("Lỗi khi lấy danh sách sách từ database: " + e.getMessage());
    }
  }

}
