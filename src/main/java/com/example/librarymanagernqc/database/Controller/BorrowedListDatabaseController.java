package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.ManagementInterface.BorrowedList.BorrowedListController;
import com.example.librarymanagernqc.ManagementInterface.User.RecordBookLoan.RecordBookLoanController;
import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.database.DAO.BorrowedListDAO;

import java.util.List;

public class BorrowedListDatabaseController extends HasError {
    private static BorrowedListDAO borrowedListDAO = new BorrowedListDAO();

    private BorrowedListDatabaseController() {
    }

    // Phương thức này được gọi để tải sách mượn từ database
    public static boolean loadBookLoanFromDatabase() {
        try {
            List<BookLoan> bookloanFromDb = borrowedListDAO.getBookLoanFromDatabase();
            // tải sách mượn vào database
            BorrowedListController.setAllBookLoanList(bookloanFromDb);
            return true;
        } catch (Exception e) {
            setErrorMessage("Error loading Loan Books from database");
            return false;
        }
    }

    // xóa book loan
    public static boolean deleteBookLoanById(BookLoan curBook) {
        if (!borrowedListDAO.deleteBookLoanById(curBook)) {
            setErrorMessage("Error deleting Book Loan");
            return false;
        }
        return true;
    }

    // kiểm tra book loan có tồn tại không với bookId không
    public static boolean isBookLoanExistBybookId(String bookId) {
        if (!borrowedListDAO.isBookLoanExistBybookId(bookId)) {
            setErrorMessage("Book Loan not found");
            return false;
        }
        return true;
    }

    // kiểm tra book loan có tồn tại không với member_name không
    public static boolean isBookLoanExistBymemberName(String member_name) {
        if (!borrowedListDAO.isBookLoanExistBymemberName(member_name)) {
            setErrorMessage("Book Loan not found");
            return false;
        }
        return true;
    }

    // insert bookloan
    public static boolean insertBookLoan(BookLoan curBookLoan) {
        if (!borrowedListDAO.insertBookLoan(curBookLoan)) {
            setErrorMessage("Error inserting Book Loan");
            return false;
        }
        return true;
    }
}
