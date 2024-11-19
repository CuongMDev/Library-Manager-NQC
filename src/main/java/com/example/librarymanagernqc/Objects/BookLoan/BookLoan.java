package com.example.librarymanagernqc.Objects.BookLoan;

import com.example.librarymanagernqc.Objects.Book.Book;
import com.example.librarymanagernqc.Objects.Utils;

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

    private static int nextId = 0;

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
        this.loanId = nextId++;
    }

    public BookLoan(String username, String bookTitle, String bookId, String loanDate, String dueDate, int loanQuantity, String fine) {
        this.username = username;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.status = "On Time";
        this.loanQuantity = loanQuantity;
        this.fine = fine;
        this.loanId = nextId++;
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

    public void setBookLoan(BookLoan bookLoan) {
        this.bookId = bookLoan.getBookId();
        this.bookTitle = bookLoan.getBookTitle();
        this.loanDate = bookLoan.getLoanDate();
        this.dueDate = bookLoan.getDueDate();
        this.status = bookLoan.getStatus();
        this.loanQuantity = bookLoan.getLoanQuantity();
        this.fine = bookLoan.getFine();
    }

    /**
     * search book loan by title, limit = 0 mean no limit
     */
    public static List<BookLoan> fuzzySearch(List<BookLoan> bookLoans, String word, int maxDistance, int limit) {
        return Utils.fuzzySearch(bookLoans, word,"getBookTitle", 0, limit);
    }
}
