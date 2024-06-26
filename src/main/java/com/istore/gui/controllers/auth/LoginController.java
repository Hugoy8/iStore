package com.istore.gui.controllers.auth;

import com.istore.gui.AppLauncher;
import com.istore.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.istore.Application.getAuthService;

public class LoginController{

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML private Text errorText;
    @FXML private HBox errorBox;

    /**
     * Connecte un utilisateur.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    public void handleLogin() throws IOException {
        User user = null;
        errorBox.setVisible(false);

        try {
             user = getAuthService().login(emailField.getText(), passwordField.getText());
        } catch (Exception e) {
            errorText.setText("Erreur durant la vérification de l'utilisateur. Veuillez ressayer plus tard.");
            errorBox.setVisible(true);
            return;
        }


        if (user != null) {
            getAuthService().setUser(user);
            AppLauncher.showDashboardView();
        } else {
            errorBox.setVisible(true);
        }
    }

    /**
     * Redirige l'utilisateur vers la page d'inscription.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    public void goToRegisterView() throws IOException {
        AppLauncher.showRegisterView();
    }
}
