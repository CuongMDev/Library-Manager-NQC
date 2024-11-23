package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.ManagementInterface.BorrowedList.BorrowedListController;
import com.example.librarymanagernqc.ManagementInterface.User.RecordBookLoan.RecordBookLoanController;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.database.DAO.BorrowedListDAO;
import java.util.List;

public class BorrowedListDatabaseController extends HasError {
  private static BorrowedListDAO borrowedListDAO = new BorrowedListDAO();

  private BorrowedListDatabaseController() {
  }

  // Phương thức này được gọi để tải sách mượn từ database
  public static boolean loadBookLoanFromDatabase() {
    try{
      List<BookLoan> bookloanFromDb = borrowedListDAO.getBookLoanFromDatabase();
      // tải sách mượn vào database
      BorrowedListController.setAllBookLoanList(bookloanFromDb);
      return true;
    }
    catch(Exception e){
      setErrorMessage("Error loading Loan Books from database");
      return false;
    }
  }

}
