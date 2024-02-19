package com.istore.gui.controllers.dashboard.popup;

import com.istore.models.User;
import com.istore.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateUserPopupController {

    @FXML private TextField pseudoField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;

    @FXML private Text errorText;
    @FXML private HBox errorBox;

    @FXML
    private void handleCreateUser() {
        errorBox.setVisible(false);

        String pseudo = pseudoField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleComboBox.getValue();

        if (!password.equals(confirmPassword)) {
            errorText.setText("Les mots de passe ne correspondent pas.");
            errorBox.setVisible(true);
            return;
        }

        String hashedPassword = com.istore.utils.HashUtil.hashPassword(password);
        if (hashedPassword == null) {
            errorText.setText("Erreur lors du hachage du mot de passe. Veuillez réessayer.");
            errorBox.setVisible(true);
            return;
        }

        User user = new User(email, pseudo, hashedPassword, role);

        try {
            Application.getUserService().createUser(user);
            closePopup();
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText("Erreur lors de la création de l'utilisateur. Veuillez réessayer.");
            errorBox.setVisible(true);
        }
    }

    private void closePopup() {
        Stage stage = (Stage) pseudoField.getScene().getWindow();
        stage.close();
    }
}