package com.istore.gui.controllers.dashboard.popup.stores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteStoreConfirmController {

    @FXML
    private Button cancelButton;

    private Runnable onConfirm;

    public void setOnConfirm(Runnable onConfirm) {
        this.onConfirm = onConfirm;
    }

    /**
     * Appelé lorsqu'on clique sur le bouton "Confirmer".
     */
    @FXML
    private void handleConfirm() {
        if (onConfirm != null) {
            onConfirm.run();
        }
        closeStage();
    }

    /**
     * Appelé lorsqu'on clique sur le bouton "Annuler".
     */
    @FXML
    private void handleCancel() {
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
