package com.example.librarymanagernqc.Objects.Book;

import com.example.librarymanagernqc.Objects.Utils;

import java.util.List;

public class Book {
    private String id;
    private String title;
    private String authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int quantity;

//    public Book() {
//        this.id = "";
//        this.title = "";
//        this.authors = "";
//        this.publisher = "";
//        this.publishedDate = "";
//        this.description = "";
//        this.count = 0;
//    }

    // Constructor
    public Book(String id, String title, String authors, String publisher, String publishedDate, String description, int quantity) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * search book by title, limit = 0 mean no limit
     */
    public static List<Book> fuzzySearch(List<Book> books, String word, int maxDistance, int limit) {
        return Utils.fuzzySearch(books, word,"getTitle", 0, limit);
    }
}
