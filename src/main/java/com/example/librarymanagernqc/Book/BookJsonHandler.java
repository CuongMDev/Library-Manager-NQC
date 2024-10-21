package com.example.librarymanagernqc.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookJsonHandler {
    private BookJsonHandler(Book book) {
    }

    static Book getBookFromJson(JSONObject item) {
        JSONObject volumeInfo = item.getJSONObject("volumeInfo");
        if (volumeInfo == null) {
            return null;
        }

        String id = item.getString("id");
        String title = volumeInfo.getString("title");
        JSONArray authorsArray = volumeInfo.getJSONArray("authors");
        String[] authors = new String[authorsArray.length()];
        for (int i = 0; i < authorsArray.length(); i++) {
            authors[i] = authorsArray.getString(i);
        }
        String publisher = volumeInfo.optString("publisher", "Unknown");
        String publishedDate = volumeInfo.optString("publishedDate", "Unknown");
        String description = volumeInfo.optString("description", "No description");

        return new Book(id, title, authors, publisher, publishedDate, description);
    }

    static public List<Book> parseBookTitles(String jsonResponse) {
        List<Book> bookList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray items = jsonObject.optJSONArray("items");

        if (items != null) {
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                bookList.add(getBookFromJson(item));
            }
        }

        return bookList;
    }

    static public Book parseBookTitle(String jsonResponse) {
        JSONObject item = new JSONObject(jsonResponse);
        Book book = getBookFromJson(item);
        return book;
    }
}
