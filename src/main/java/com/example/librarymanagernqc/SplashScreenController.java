package com.example.librarymanagernqc;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SplashScreenController {
    public void showSplashScreen(Stage stage) {
        try {
            // Tải FXML cho màn hình chào
            Parent splashScreen = FXMLLoader.load(getClass().getResource("splash-screen.fxml"));
            Scene splashScene = new Scene(splashScreen);

            stage.setScene(splashScene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            // Thiết lập thời gian hiển thị
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                // Chuyển đến màn hình chính
                try {
                    stage.close();

                    Stage mainStage = new Stage();
                    Parent mainScreen = FXMLLoader.load(getClass().getResource("login.fxml"));
                    mainStage.getIcons().add(new Image(getClass().getResourceAsStream("icons/app-icon.png")));
                    mainStage.setTitle("Library Manager NQC");
                    mainStage.setScene(new Scene(mainScreen));
                    mainStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
