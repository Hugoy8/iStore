package com.istore.gui.controllers.dashboard.popup.users;

import com.istore.gui.AppLauncher;
import com.istore.models.User;
import com.istore.services.AuthService;
import com.istore.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EditUserPopupController {

    @FXML private TextField pseudoField;
    @FXML private TextField emailField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Text errorText;
    @FXML private HBox errorBox;
    @FXML private VBox roleBox;

    private User currentUser;

    /**
     * Initialise les données de l'utilisateur à modifier.
     * @param user L'utilisateur à modifier.
     */
    public void initUserData(User user) throws IOException {
        this.currentUser = user;
        pseudoField.setText(user.getPseudo());
        emailField.setText(user.getEmail());

        if (!Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            this.roleBox.setManaged(false);
            this.roleBox.setVisible(false);
        }

        if (Objects.equals(user.getRole(), "admin")){
            this.roleComboBox.setValue("Administrateur");
        } else {
            this.roleComboBox.setValue("Utilisateur");
        }

    }

    /**
     * Met à jour les informations de l'utilisateur.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    private void handleUpdateUser() throws IOException {
        errorBox.setVisible(false);
        String pseudo = pseudoField.getText();
        String email = emailField.getText();
        String role = roleComboBox.getValue();

        if (Objects.equals(role, "Administrateur")){
            if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
                role = "admin";
            } else {
                closePopup();
                AppLauncher.showAdminError();
            }
        } else {
            role = "user";
        }

        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        currentUser.setPseudo(pseudo);
        currentUser.setEmail(email);
        currentUser.setRole(role);

        try {
            if (!newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    errorText.setText("Les mots de passe ne correspondent pas.");
                    errorBox.setVisible(true);
                    return;
                }
                String updatePasswordResult = Application.getAuthService().updateUserPassword(currentUser.getId(), newPassword);
                if (!updatePasswordResult.equals("Mot de passe mis à jour avec succès.")) {
                    errorText.setText(updatePasswordResult);
                    errorBox.setVisible(true);
                    return;
                }
            }

            Application.getUserService().updateUser(currentUser);
            closePopup();
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText("Erreur lors de la mise à jour de l'utilisateur.");
            errorBox.setVisible(true);
        }
    }

    /**
     * Ferme la popup.
     */
    private void closePopup() {
        Stage stage = (Stage) pseudoField.getScene().getWindow();
        stage.close();
    }
}