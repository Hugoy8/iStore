package com.istore.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class AppLauncher extends Application {

    private static Stage primaryStage;
    private static double WINDOW_WIDTH = 1240;
    private static double WINDOW_HEIGHT = 824;

    private static String baseTitle = "iStore";
    public static void setTitle(String specificTitle) {
        primaryStage.setTitle(baseTitle + " - " + specificTitle);
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        // Taille de la fenêtre.
        WINDOW_WIDTH = Screen.getPrimary().getBounds().getWidth();
        WINDOW_HEIGHT = Screen.getPrimary().getBounds().getHeight();

        showLoginView();
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    /**
     * Change la scène pour afficher la page de connexion.
     */
    public static void showLoginView() throws IOException {
        Parent loginView = FXMLLoader.load(Objects.requireNonNull(AppLauncher.class.getResource("/com/istore/views/auth/login-view.fxml")));
        Scene scene = new Scene(loginView, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.getStylesheets().add(Objects.requireNonNull(AppLauncher.class.getResource("/com/istore/styles/auth.css")).toExternalForm());

        primaryStage.setScene(scene);
        setTitle("Connexion");
    }

    /**
     * Change la scène pour afficher la page d'inscription.
     */
    public static void showRegisterView() throws IOException {
        Parent registerView = FXMLLoader.load(Objects.requireNonNull(AppLauncher.class.getResource("/com/istore/views/auth/register-view.fxml")));
        Scene scene = new Scene(registerView, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.getStylesheets().add(Objects.requireNonNull(AppLauncher.class.getResource("/com/istore/styles/auth.css")).toExternalForm());

        primaryStage.setScene(scene);
        setTitle("Inscription");
    }

    /**
     * Change la scène pour afficher la page de base du dashboard.
     */
    public static void showDashboardView() throws IOException {
        Parent dashboardView = FXMLLoader.load(Objects.requireNonNull(AppLauncher.class.getResource("/com/istore/views/dashboard/dashboard-view.fxml")));
        Scene scene = new Scene(dashboardView, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.getStylesheets().add(Objects.requireNonNull(AppLauncher.class.getResource("/com/istore/styles/dashboard.css")).toExternalForm());

        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}