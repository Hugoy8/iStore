package com.istore.gui.controllers.dashboard.popup;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminErrorPopupController {

    @FXML
    private Button cancelButton;

    /**
     * Appelé lorsqu'on clique sur le bouton "D'accord".
     */
    @FXML
    private void handleConfirm() {
        closeStage();
    }

    /**
     * Ferme la fenêtre de popup.
     */
    private void closeStage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
