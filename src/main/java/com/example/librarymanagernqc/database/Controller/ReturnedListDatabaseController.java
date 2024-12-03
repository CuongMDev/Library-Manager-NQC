package com.example.librarymanagernqc.database.Controller;

import com.example.librarymanagernqc.AbstractClass.HasError;
import com.example.librarymanagernqc.ManagementInterface.ReturnedList.ReturnedListController;
import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.database.DAO.ReturnedListDAO;

import java.lang.ModuleLayer.Controller;
import java.util.List;

public class ReturnedListDatabaseController extends HasError {
    private static ReturnedListDAO returnedListDAO = new ReturnedListDAO();

    private static ReturnedListDatabaseController instance;

    private ReturnedListDatabaseController() {

    }

    public static ReturnedListDatabaseController getInstance() {
        if (instance == null) {
            instance = new ReturnedListDatabaseController();
        }
        return instance;
    }

    // phương thức này được gọi để tải sách trả từ database
    public boolean loadBookReturnFromDatabase() {
        try {
            List<BookLoan> bookreturnFromDb = returnedListDAO.getBookReturnFromDatabase();
            // tải sách trả vào database
            ReturnedListController.setAllBookReturnList(bookreturnFromDb);
            return true;
        } catch (Exception e) {
            setErrorMessage("Error loading Loan Books from database");
            return false;
        }
    }

    public boolean insertBookReturn(BookLoan bookLoan) {
        if (!returnedListDAO.insertBookReturn(bookLoan)) {
            setErrorMessage("Error inserting book return");
            return false;
        }
        return true;
    }
}
