package com.istore.gui.controllers.dashboard.popup.stores;

import com.istore.Application;
import com.istore.models.Store;
import com.istore.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

import java.util.List;

public class CreateStorePopupController {

    @FXML private TextField nameField;
    @FXML private TextField locationField;

    @FXML private Text errorText;
    @FXML private HBox errorBox;

    /**
     * Crée un magasin.
     */
    @FXML
    private void handleCreateStore() {
        errorBox.setVisible(false);

        String name = nameField.getText();
        String location = locationField.getText();
        List<String> employees = new ArrayList<>();

        Store store = new Store(name, location, employees);

        try {
            Application.getStoreService().createStore(store);
            closePopup();
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText("Erreur lors de la création du magasin. Veuillez réessayer.");
            errorBox.setVisible(true);
        }
    }

    /**
     * Ferme la fenêtre de popup.
     */
    private void closePopup() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}