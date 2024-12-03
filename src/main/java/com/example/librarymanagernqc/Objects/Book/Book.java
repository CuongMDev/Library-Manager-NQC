package com.example.librarymanagernqc.Objects.Book;

import com.example.librarymanagernqc.Objects.FuzzySearch;

import java.util.List;

public class Book extends BookInfo {
    private int quantity;

    public Book(String id, String title, String authors, String publisher, String publishedDate, String description, int quantity, String thumbnailUrl, String infoLink) {
        super(id, title, authors, publisher, publishedDate, description, thumbnailUrl, infoLink);
        this.quantity = quantity;
    }

    public Book(BookInfo bookInfo, int quantity) {
        super(bookInfo);
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    /**
     * search book by title, limit = 0 mean no limit
     */
    public static List<Book> fuzzySearch(List<Book> books, String word, int maxDistance, int limit) {
        return FuzzySearch.search(books, word,"getTitle", 0, limit);
    }
}
