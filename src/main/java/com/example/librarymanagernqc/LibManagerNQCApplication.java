package com.example.librarymanagernqc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LibManagerNQCApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String mode = System.getProperty("mode", "release");
        if ("debug".equalsIgnoreCase(mode)) {
            Parent splashScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ManagementInterface/main-pane.fxml")));
            Scene splashScene = new Scene(splashScreen);

            stage.setTitle("Library Manager NQC");
            stage.setScene(splashScene);
            stage.show();
        } else if ("release".equalsIgnoreCase(mode)) {
            SplashScreenController splashController = new SplashScreenController();
            splashController.showSplashScreen(stage);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}