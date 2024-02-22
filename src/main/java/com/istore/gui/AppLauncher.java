package com.istore.gui;

import com.istore.gui.controllers.dashboard.popup.users.DeleteUserConfirmController;
import com.istore.models.User;
import javafx.application.Application;
import javafx.scene.Group;
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

        // Initialisation de la scène principale avec un contenu vide ou une vue de démarrage
        Scene scene = new Scene(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);

        showLoginView();
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    /**
     * Change la scène pour afficher le contenu d'une vue.
     * @param fxmlFile Le fichier FXML de la vue.
     * @param cssFile Le fichier CSS de la vue.
     * @param title Le titre de la vue.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    private static void changeSceneContent(String fxmlFile, String cssFile, String title) throws IOException {
        Scene scene = primaryStage.getScene();
        scene.setRoot(FXMLLoader.load(Objects.requireNonNull(AppLauncher.class.getResource(fxmlFile))));

        scene.getStylesheets().clear();
        scene.getStylesheets().add(Objects.requireNonNull(AppLauncher.class.getResource(cssFile)).toExternalForm());

        setTitle(title);
    }

    /**
     * Change la scène pour afficher la page de connexion.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    public static void showLoginView() throws IOException {
        changeSceneContent("/com/istore/views/auth/login-view.fxml", "/com/istore/styles/auth.css", "Connexion");
    }

    /**
     * Change la scène pour afficher la page d'inscription.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    public static void showRegisterView() throws IOException {
        changeSceneContent("/com/istore/views/auth/register-view.fxml", "/com/istore/styles/auth.css", "Inscription");
    }

    /**
     * Change la scène pour afficher la page de base du dashboard.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    public static void showDashboardView() throws IOException {
        changeSceneContent("/com/istore/views/dashboard/dashboard-view.fxml", "/com/istore/styles/dashboard.css", "Tableau de bord");
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