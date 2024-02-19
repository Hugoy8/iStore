package com.istore.gui.controllers.dashboard;

import com.istore.Application;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.SQLException;

public class ManagementViewController {

    @FXML private TextField emailField;
    @FXML private HBox errorBox, successBox;
    @FXML private Text errorText, successText;

    /**
     * Ajoute une adresse e-mail à la liste blanche.
     */
    @FXML
    private void handleWhitelist() {
        String email = emailField.getText().trim();
        try {
            Application.getUserService().addEmailToWhitelist(email);
            showErrorBox(false);
            showSuccessBox(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                emailField.clear();
                showSuccessBox(false);
            });
            pause.play();
        } catch (SQLException e) {
            showSuccessBox(false);
            showErrorBox(true);
        }
    }

    /**
     * Affiche ou masque la boîte d'erreur.
     * @param show true pour afficher, false pour masquer.
     */
    private void showErrorBox(boolean show) {
        errorBox.setVisible(show);
        errorBox.setManaged(show);
        if (show) {
            errorText.setText("L'adresse e-mail est déjà dans la liste blanche.");
        }
    }

    /**
     * Affiche ou masque la boîte de succès.
     * @param show true pour afficher, false pour masquer.
     */
    private void showSuccessBox(boolean show) {
        successBox.setVisible(show);
        successBox.setManaged(show);
        if (show) {
            successText.setText("E-mail ajouté avec succès à la liste blanche !");
        }
    }
}
