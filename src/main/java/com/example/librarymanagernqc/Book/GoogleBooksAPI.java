package com.example.librarymanagernqc.Book;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.json.JSONArray;

public class GoogleBooksAPI {
    public interface ApiCallback {
        void onSuccess(String result);
        void onError(Exception e);
    }

    private static GoogleBooksAPI instance;
    private static final String API_KEY = "API_KEY";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    private final HttpClient client;
    private final ExecutorService executorService;

    // Private constructor to prevent instantiation
    private GoogleBooksAPI() {
        client = HttpClient.newHttpClient();
        executorService = Executors.newFixedThreadPool(50); // Số luồng tối đa có thể chạy
    }

    // Method to get the single instance of GoogleBooksAPI
    public static synchronized GoogleBooksAPI getInstance() {
        if (instance == null) {
            instance = new GoogleBooksAPI();
        }
        return instance;
    }

    // Phương thức tìm kiếm sách theo tiêu đề và tác giả
    public void searchBooks(String title, String author, ApiCallback callback) {
        String query = "";
        if (!title.isEmpty() && !author.isEmpty()) {
            query = String.format("?q=intitle:%s+inauthor:%s&", title, author);
        } else if (title.isEmpty()) {
            query = String.format("?q=intitle:%s", title);
        }
        else if (author.isEmpty()) {
            query = String.format("?q=inauthor:%s", title);
        }

        sendRequest(query, callback);
    }

    // Phương thức tìm kiếm sách theo ID
    public void searchBookById(String Id, ApiCallback callback) {
        String query = String.format("/%s?", Id);
        sendRequest(query, callback);
    }

    // Phương thức gửi yêu cầu đến API
    private void sendRequest(String query, ApiCallback callback) {
        String url = String.format("%s%skey=%s", BASE_URL, query, API_KEY);

        executorService.submit(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String jsonResponse = response.body();
                callback.onSuccess(jsonResponse); // Gọi callback khi thành công
            } catch (Exception e) {
                callback.onError(e); // Gọi callback khi có lỗi
            }
        });
    }

    // Đóng ExecutorService khi không còn sử dụng
    public void shutdown() {
        executorService.shutdown();
    }
}
