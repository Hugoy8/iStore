package com.istore.gui.controllers.dashboard;

import com.istore.Application;
import com.istore.services.AuthService;
import com.istore.services.UserService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.SQLException;

public class ManagementViewController {
    @FXML
    private TextField emailField;
    @FXML
    private HBox errorBox, successBox;
    @FXML
    private Text errorText, successText;

    UserService userService = Application.getUserService();


    @FXML
    private void handleWhitelist() {
        String email = emailField.getText().trim();
        try {
            userService.addEmailToWhitelist(email);
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

    private void showErrorBox(boolean show) {
        errorBox.setVisible(show);
        errorBox.setManaged(show);
        if (show) {
            errorText.setText("L'adresse e-mail est déjà dans la liste blanche.");
        }
    }

    private void showSuccessBox(boolean show) {
        successBox.setVisible(show);
        successBox.setManaged(show);
        if (show) {
            successText.setText("E-mail ajouté avec succès à la liste blanche !");
        }
    }
}
