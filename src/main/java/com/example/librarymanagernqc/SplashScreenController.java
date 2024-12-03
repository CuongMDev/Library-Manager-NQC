package com.example.librarymanagernqc;

import com.example.librarymanagernqc.AbstractClass.Controller;
import com.example.librarymanagernqc.Login.LoginController;
import com.example.librarymanagernqc.TimeGetter.TimeGetter;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class SplashScreenController extends Controller {
    public void showSplashScreen() {
        try {
            Scene splashScene = new Scene(getParent());
            Stage stage = getStage();

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

                        Stage newStage = new Stage();
                        LoginController loginController = (LoginController)Controller.init(newStage, getClass().getResource("Login/login.fxml"));
                        newStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("common/images/app-icon.png"))));
                        newStage.setTitle("Library Manager NQC");
                        newStage.setScene(new Scene(loginController.getParent()));
                        newStage.setResizable(false);
                        newStage.show();
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
