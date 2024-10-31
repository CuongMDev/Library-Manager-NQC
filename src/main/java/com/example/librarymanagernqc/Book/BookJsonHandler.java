package com.example.librarymanagernqc.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookJsonHandler {
    private BookJsonHandler(Book book) {
    }

    static Book getBookFromJson(JSONObject item) {
        if (!item.has("volumeInfo")) {
            return null;
        }
        JSONObject volumeInfo = item.getJSONObject("volumeInfo");
        if (volumeInfo == null) {
            return null;
        }

        String id = item.getString("id");
        String title = volumeInfo.getString("title");
        StringBuilder authors;
        if (volumeInfo.has("authors")) {
            authors = new StringBuilder();
            JSONArray authorsArray = volumeInfo.getJSONArray("authors");
            for (int i = 0; i < authorsArray.length(); i++) {
                authors.append(authorsArray.getString(i));
                if (i < authorsArray.length() - 1) {
                    authors.append(", ");
                }
            }
        } else {
            authors = new StringBuilder("Unknown");
        }
        String publisher = volumeInfo.optString("publisher", "Unknown");
        String publishedDate = volumeInfo.optString("publishedDate", "Unknown");
        String description = volumeInfo.optString("description", "No description");

        return new Book(id, title, authors.toString(), publisher, publishedDate, description);
    }

    static public List<Book> parseBookTitles(String jsonResponse) {
        List<Book> bookList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        if (jsonObject.has("items")) {
            JSONArray items = jsonObject.optJSONArray("items");

            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    bookList.add(getBookFromJson(item));
                }
            }
        }
        else {
            Book book = getBookFromJson(jsonObject);
            if (book != null) {
                bookList.add(book);
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
