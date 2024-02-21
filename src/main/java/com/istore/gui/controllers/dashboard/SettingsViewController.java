package com.istore.gui.controllers.dashboard;

import com.istore.models.User;
import com.istore.utils.HashUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static com.istore.Application.getAuthService;

public class SettingsViewController {

    @FXML private TextField emailField, pseudoField;
    @FXML private PasswordField actualPasswordField, newPasswordField, confirmNewPasswordField;
    @FXML private HBox errorInformationBox, successInformationBox, errorSecurityBox, successSecurityBox;
    @FXML private VBox informationBox, securityBox;
    @FXML private Button informationButton, securityButton;
    @FXML private Text errorInformationText, successInformationText, errorSecurityText, successSecurityText;

    /**
     * Initialisation de la vue.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    public void initialize() throws IOException {
        this.handleInformationSection();
        emailField.setText(getAuthService().getUser().getEmail());
        pseudoField.setText(getAuthService().getUser().getPseudo());
    }

    /**
     * Gère la modification des informations de l'utilisateur.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    public void handleInformation() throws SQLException, IOException {
        this.hideAllBox();
        User user = getAuthService().getUser();

        // Vérification des nouvelles informations.
        if (emailField.getText().isEmpty() || pseudoField.getText().isEmpty()) {
            showErrorInformationBox(true, "Veuillez remplir tous les champs.");
            return;
        }
        if (Objects.equals(user.getEmail(), emailField.getText()) && Objects.equals(user.getPseudo(), pseudoField.getText())) {
            showErrorInformationBox(true, "Aucune modification n'a été apportée.");
            return;
        }

        user.setEmail(emailField.getText());
        user.setPseudo(pseudoField.getText());

        String result = getAuthService().updateUser(getAuthService().getUser(), user);

        if (result.equals("Utilisateur mis à jour avec succès.")){
            showSuccessInformationBox(true, result);
            getAuthService().reloadUser();
        } else {
            showErrorInformationBox(true, result);
        }
    }

    /**
     * Gère la modification du mot de passe.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    public void handlePassword() throws IOException, SQLException {
        this.hideAllBox();
        User user = getAuthService().getUser();

        // Vérification des nouvelles informations
        if (actualPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty() || confirmNewPasswordField.getText().isEmpty()) {
            showErrorSecurityBox(true, "Veuillez remplir tous les champs.");
            return;
        }
        if (!HashUtil.checkPassword(actualPasswordField.getText(), user.getPasswordHash())) {
            showErrorSecurityBox(true, "Veuillez vérifier votre mot de passe actuel.");
            return;
        }
        if (!newPasswordField.getText().equals(confirmNewPasswordField.getText())) {
            showErrorSecurityBox(true, "Les nouveaux mots de passe ne correspondent pas.");
            return;
        }
        if (HashUtil.checkPassword(newPasswordField.getText(), user.getPasswordHash())) {
            showErrorInformationBox(true, "Aucune modification n'a été apportée.");
            return;
        }

        // Mise à jour du mot de passe
        String result = getAuthService().updateUserPassword(user.getId(), newPasswordField.getText());

        if (result.equals("Mot de passe mis à jour avec succès.")){
            showSuccessSecurityBox(true, result);
            getAuthService().reloadUser();
        } else {
            showErrorSecurityBox(true, result);
        }
    }

    /**
     * Affiche ou masque la boîte d'erreur pour les informations.
     * @param show true pour afficher, false pour masquer.
     * @param message Le message a afficher.
     */
    private void showErrorInformationBox(boolean show, String message) {
        errorInformationBox.setVisible(show);
        errorInformationBox.setManaged(show);
        if (show) {
            errorInformationText.setText(message);
        }
    }

    /**
     * Affiche ou masque la boîte de succès pour les informations.
     * @param show true pour afficher, false pour masquer.
     * @param message Le message a afficher.
     */
    private void showSuccessInformationBox(boolean show, String message) {
        successInformationBox.setVisible(show);
        successInformationBox.setManaged(show);
        if (show) {
            successInformationText.setText(message);
        }
    }

    /**
     * Affiche ou masque la boîte d'erreur.
     * @param show true pour afficher, false pour masquer.
     * @param message Le message a afficher.
     */
    private void showErrorSecurityBox(boolean show, String message) {
        errorSecurityBox.setVisible(show);
        errorSecurityBox.setManaged(show);
        if (show) {
            errorSecurityText.setText(message);
        }
    }

    /**
     * Affiche ou masque la boîte de succès.
     * @param show true pour afficher, false pour masquer.
     * @param message Le message a afficher.
     */
    private void showSuccessSecurityBox(boolean show, String message) {
        successSecurityBox.setVisible(show);
        successSecurityBox.setManaged(show);
        if (show) {
            successSecurityText.setText(message);
        }
    }

    /**
     * Permet de masquer toutes les boîtes.
     */
    private void hideAllBox() {
        showErrorInformationBox(false, "");
        showSuccessInformationBox(false, "");
        showErrorSecurityBox(false, "");
        showSuccessSecurityBox(false, "");
    }

    /**
     * Permet d'afficher la section d'information et de masquer les autres sections.
     */
    @FXML
    public void handleInformationSection() {
        this.informationButton.setTextFill(Color.web("#007AFF"));
        this.securityButton.setTextFill(Color.web("#818181"));

        this.informationBox.setVisible(true);
        this.informationBox.setManaged(true);
        this.securityBox.setVisible(false);
        this.securityBox.setManaged(false);
    }

    /**
     * Permet d'afficher la section d'information et de masquer les autres sections.
     */
    @FXML
    public void handleSecuritySection() {
        this.securityButton.setTextFill(Color.web("#007AFF"));
        this.informationButton.setTextFill(Color.web("#818181"));

        this.informationBox.setVisible(false);
        this.informationBox.setManaged(false);
        this.securityBox.setVisible(true);
        this.securityBox.setManaged(true);
    }
}
