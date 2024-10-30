package com.example.librarymanagernqc;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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

            // Thiết lập thời gian hiển thị
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                // Chuyển đến màn hình chính
                try {
                    stage.close();

                    Stage mainStage = new Stage();
                    Parent mainScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login/login.fxml")));
                    mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("common/images/app-icon.png"))));
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
