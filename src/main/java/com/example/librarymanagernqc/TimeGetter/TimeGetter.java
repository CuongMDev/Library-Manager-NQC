package com.example.librarymanagernqc.TimeGetter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeGetter {
    private static final String timeUrl = "https://timeapi.io/api/Time/current/zone?timeZone=Asia/Bangkok";
    private static final HttpClient client = HttpClient.newHttpClient();

    private static LocalDateTime currentTime;

    private TimeGetter() {}

    // Phương thức gửi yêu cầu đến API
    public static Task<Void> fetchCurrentTimeFromServer() throws IOException, InterruptedException {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(timeUrl))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Phân tích kết quả JSON để lấy thời gian
                JSONObject jsonResponse = new JSONObject(response.body());
                String dateTimeString = jsonResponse.getString("dateTime");

                // Chuyển đổi chuỗi thời gian sang LocalDateTime
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                currentTime = LocalDateTime.parse(dateTimeString, formatter);
                executeTimeIncrement();

                return null;
            }
        };
    }

    private static void executeTimeIncrement() {
        // Tạo Timeline để cập nhật thời gian mỗi giây
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            currentTime = currentTime.plusSeconds(1); // Cộng 1 giây vào currentTime
        }));

        // Đặt Timeline chạy liên tục
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
