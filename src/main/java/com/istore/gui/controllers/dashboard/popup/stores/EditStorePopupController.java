package com.istore.gui.controllers.dashboard.popup.stores;

import com.istore.Application;
import com.istore.models.User;
import com.istore.services.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditStorePopupController {

    @FXML private TextField pseudoField;
    @FXML private TextField emailField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Text errorText;
    @FXML private HBox errorBox;

    private User currentUser;

    public void initUserData(User user) {
        this.currentUser = user;
        pseudoField.setText(user.getPseudo());
        emailField.setText(user.getEmail());
        roleComboBox.setValue(user.getRole());
    }

    @FXML
    private void handleUpdateUser() {
        errorBox.setVisible(false);
        String pseudo = pseudoField.getText();
        String email = emailField.getText();
        String role = roleComboBox.getValue();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        AuthService authService = Application.getAuthService();

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
                String updatePasswordResult = authService.updateUserPassword(currentUser.getId(), newPassword);
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

    private void closePopup() {
        Stage stage = (Stage) pseudoField.getScene().getWindow();
        stage.close();
    }
}