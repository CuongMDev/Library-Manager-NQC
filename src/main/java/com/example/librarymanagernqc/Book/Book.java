package com.example.librarymanagernqc.Book;

public class Book {
    private String id;
    private String title;
    private String[] authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int count;

    // Constructor
    public Book(String id, String title, String[] authors, String publisher, String publishedDate, String description) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;

        this.count = 0;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String[] getAuthors() { return authors; }
    public void setAuthors(String[] authors) { this.authors = authors; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
