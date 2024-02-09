package com.istore.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class AppLauncher extends Application {

    private static Stage primaryStage;
    private static final double WINDOW_WIDTH = 1240;
    private static final double WINDOW_HEIGHT = 824;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showLoginView();
        primaryStage.show();
    }

    public static void showLoginView() throws IOException {
        Parent loginView = FXMLLoader.load(AppLauncher.class.getResource("/com/istore/login-view.fxml"));
        Scene scene = new Scene(loginView, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.getStylesheets().add(AppLauncher.class.getResource("/com/istore/styles/auth.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Connexion");
    }

    public static void showRegisterView() throws IOException {
        Parent registerView = FXMLLoader.load(AppLauncher.class.getResource("/com/istore/register-view.fxml"));
        Scene scene = new Scene(registerView, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.getStylesheets().add(AppLauncher.class.getResource("/com/istore/styles/auth.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscription");
    }

    public static void main(String[] args) {
        launch(args);
    }
}