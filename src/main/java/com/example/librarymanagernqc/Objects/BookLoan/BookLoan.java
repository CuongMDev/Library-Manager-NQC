package com.example.librarymanagernqc.Objects.BookLoan;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.Utils;

import com.example.librarymanagernqc.database.DAO.IdGeneratorDAO;
import java.util.List;

public class BookLoan {
    private String username;
    private String bookTitle;
    private String loanDate;
    private String dueDate;
    private String status;
    private int loanQuantity;
    private String bookId;
    private String fine;
    private String bookCondition;
    private int loanId;

    private IdGeneratorDAO idGeneratorDAO = new IdGeneratorDAO();


    // dùng cho borrowed list
    public BookLoan(String username, String bookTitle, String bookId, String loanDate, String dueDate, int loanQuantity) {
        this.username = username;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.status = "On Time";
        this.loanQuantity = loanQuantity;
        this.fine = "";
        this.bookCondition = "100%";
        this.loanId = idGeneratorDAO.getNextId();
        idGeneratorDAO.updateNextId(loanId + 1);
    }

    // dùng khi lấy dữ liệu từ database
    public BookLoan(int loanId, String username, String bookTitle, String bookId, String loanDate, String dueDate, int loanQuantity) {
        this.loanId = loanId;
        this.username = username;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.status = "On Time";
        this.loanQuantity = loanQuantity;
        this.fine = "";
        this.bookCondition = "100%";
    }

    // dùng cho return list
    public BookLoan(String username, String bookTitle, String bookId, String loanDate, String dueDate, int loanQuantity, String fine) {
        this.username = username;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.status = "On Time";
        this.loanQuantity = loanQuantity;
        this.fine = fine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLoanQuantity() {
        return loanQuantity;
    }

    public void setLoanQuantity(int loanQuantity) {
        this.loanQuantity = loanQuantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public int getLoanId() {
        return loanId;
    }

    /**
     * search book loan by title, limit = 0 mean no limit
     */
    public static List<BookLoan> fuzzySearch(List<BookLoan> bookLoans, String word, int maxDistance, int limit) {
        return Utils.fuzzySearch(bookLoans, word,"getBookTitle", 0, limit);
    }
}
