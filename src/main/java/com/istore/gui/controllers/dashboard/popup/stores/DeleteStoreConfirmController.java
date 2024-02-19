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

    @FXML
    private void handleConfirm() {
        if (onConfirm != null) {
            onConfirm.run();
        }
        closeStage();
    }

    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
