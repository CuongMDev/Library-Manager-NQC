package com.example.librarymanagernqc.Book;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javafx.concurrent.Task;
import org.json.JSONObject;

public class GoogleBooksAPI {
    private static final String API_KEY = "AIzaSyDEiN8yDVpW6M-txU1XUUGjwKWUmyhUC5A";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    private static final HttpClient client = HttpClient.newHttpClient();

    // Private constructor to prevent instantiation
    private GoogleBooksAPI() {
    }

    // Phương thức tìm kiếm sách theo tiêu đề và tác giả
    public static Task<String> searchBooks(String title, String author) {
        title = title.replace(" ", "+");
        author = author.replace(" ", "%20");

        String query = "";
        if (!title.isEmpty() && !author.isEmpty()) {
            query = String.format("?q=intitle:%s+inauthor:%s&", title, author);
        } else if (!title.isEmpty()) {
            query = String.format("?q=intitle:%s&", title);
        }
        else if (!author.isEmpty()) {
            query = String.format("?q=inauthor:%s&", author);
        }

        return sendRequest(query);
    }

    // Phương thức tìm kiếm sách theo ID
    public static Task<String> searchBookById(String id) {
        id = id.replace(" ", "%20");

        String query = String.format("/%s?", id);
        return sendRequest(query);
    }

    // Phương thức gửi yêu cầu đến API
    private static Task<String> sendRequest(String query) {
        String url = String.format("%s%skey=%s", BASE_URL, query, API_KEY);

        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String jsonResponse = response.body();
                    return jsonResponse;
                } catch (Exception e) {
                    throw e;
                }
            }
        };
    }
}
