package com.example.librarymanagernqc.Objects.Book;

import com.example.librarymanagernqc.Objects.Utils;

import java.util.List;

public class BookInfo {
    protected String id;
    protected String title;
    protected String authors;
    protected String publisher;
    protected String publishedDate;
    protected String description;
    protected String thumbnailUrl;
    protected String infoLink;

    public BookInfo(String id, String title, String authors, String publisher, String publishedDate, String description, String thumbnailUrl, String infoLink) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.infoLink = infoLink;
    }

    public BookInfo(BookInfo bookInfo) {
        this.id = bookInfo.getId();
        this.title = bookInfo.getTitle();
        this.authors = bookInfo.getAuthors();
        this.publisher = bookInfo.getPublisher();
        this.publishedDate = bookInfo.getPublishedDate();
        this.description = bookInfo.getDescription();
        this.thumbnailUrl = bookInfo.getThumbnailUrl();
        this.infoLink = bookInfo.getInfoLink();
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

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getInfoLink() { return infoLink; }
    public void setInfoLink(String infoLink) { this.infoLink = infoLink; }
}
