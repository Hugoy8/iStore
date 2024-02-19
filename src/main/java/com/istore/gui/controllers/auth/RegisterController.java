package com.istore.gui.controllers.auth;

import com.istore.gui.AppLauncher;
import com.istore.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static com.istore.Application.getAuthService;

public class RegisterController {

    // Champ de texte.
    @FXML private TextField pseudoField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    // Zone de texte pour les erreurs.
    @FXML private Text errorText;
    @FXML private HBox errorBox;

    @FXML
    public void handleRegister() throws IOException, SQLException {
        errorBox.setVisible(false);

        // Vérification des mots de passe.
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorText.setText("Les mots de passe ne correspondent pas.");
            errorBox.setVisible(true);
            return;
        }

        String error = getAuthService().register(new User(emailField.getText(), pseudoField.getText(), passwordField.getText(), "user"));

        if (Objects.equals(error, "Utilisateur enregistré avec succès.")) {
            this.goToLoginView();
        } else {
            errorText.setText(error);
            errorBox.setVisible(true);
        }
    }

    /**
     * Redirige l'utilisateur vers la page de connexion.
     */
    public void goToLoginView() throws IOException {
        AppLauncher.showLoginView();
    }
}
