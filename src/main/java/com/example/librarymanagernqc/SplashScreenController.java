package com.example.librarymanagernqc;

import com.example.librarymanagernqc.TimeGetter.TimeGetter;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class SplashScreenController {
    public void showSplashScreen(Stage stage) {
        try {
            // Tải FXML cho màn hình chào
            Parent splashScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SplashScreen/splash-screen.fxml")));
            Scene splashScene = new Scene(splashScreen);

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("common/images/app-icon.png"))));
            stage.setScene(splashScene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            try {
                //lấy thời gian từ server
                Task<Void> fetchTimeTask = TimeGetter.fetchCurrentTimeFromServer();

                fetchTimeTask.setOnSucceeded(event -> {
                    // Chuyển đến màn hình chính
                    try {
                        stage.close();

                        Stage mainStage = new Stage();
                        Parent mainScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login/login.fxml")));
                        mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("common/images/app-icon.png"))));
                        mainStage.setTitle("Library Manager NQC");
                        mainStage.setScene(new Scene(mainScreen));
                        mainStage.setResizable(false);
                        mainStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                fetchTimeTask.setOnFailed(event -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Please recheck your Internet Connection");
                    alert.setContentText(fetchTimeTask.getException().getMessage());
                    alert.showAndWait();

                    stage.close();
                });

                // Chạy task trong một luồng nền
                new Thread(fetchTimeTask).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
