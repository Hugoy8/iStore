package com.istore.gui;

import com.istore.gui.controllers.dashboard.popup.users.DeleteUserConfirmController;
import com.istore.models.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
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

    /**
     * Change le titre de la fenêtre.
     * @param specificTitle Le titre spécifique à ajouter.
     */
    public static void setTitle(String specificTitle) {
        primaryStage.setTitle(baseTitle + " - " + specificTitle);
    }

    /**
     * Démarre l'application.
     * @param stage La scène principale.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
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
     * @throws IOException Exception qui gère les erreurs de changement de vue
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
     * @throws IOException Exception qui gère les erreurs de changement de vue
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
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    public static void showDashboardView() throws IOException {
        Parent dashboardView = FXMLLoader.load(Objects.requireNonNull(AppLauncher.class.getResource("/com/istore/views/dashboard/dashboard-view.fxml")));
        Scene scene = new Scene(dashboardView, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.getStylesheets().add(Objects.requireNonNull(AppLauncher.class.getResource("/com/istore/styles/dashboard.css")).toExternalForm());

        primaryStage.setScene(scene);
    }

    /**
     * Permet d'afficher la fênetre d'erreur pour une action requiert en administrateur.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    public static void showAdminError() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(AppLauncher.class.getResource("/com/istore/views/dashboard/popup/admin-error.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Attention");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Fonction principale.
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }
}