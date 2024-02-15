package com.istore.gui.controllers.dashboard.popup;

import com.istore.models.User;
import com.istore.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditUserPopupController {

    @FXML private TextField pseudoField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Text errorText;
    @FXML private HBox errorBox;

    private User currentUser;

    public void initUserData(User user) {
        this.currentUser = user;
        pseudoField.setText(user.getPseudo());
        emailField.setText(user.getEmail());
        roleComboBox.setValue(user.getRole());
        // Assurez-vous de ne pas pré-remplir les champs de mot de passe
    }

    @FXML
    private void handleUpdateUser() {
        errorBox.setVisible(false);
        String pseudo = pseudoField.getText();
        String email = emailField.getText();
        String role = roleComboBox.getValue();

        currentUser.setPseudo(pseudo);
        currentUser.setEmail(email);
        // Ne mettez pas à jour le mot de passe ici
        currentUser.setRole(role);

        try {
            // Mise à jour des informations de l'utilisateur sans mot de passe
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
