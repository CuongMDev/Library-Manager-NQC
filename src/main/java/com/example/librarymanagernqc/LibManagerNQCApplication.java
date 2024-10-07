package com.example.librarymanagernqc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibManagerNQCApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SplashScreenController splashController = new SplashScreenController();
        splashController.showSplashScreen(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}