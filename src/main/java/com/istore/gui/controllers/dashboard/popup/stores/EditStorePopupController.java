package com.istore.gui.controllers.dashboard.popup.stores;

import com.istore.Application;
import com.istore.models.Store;
import com.istore.services.StoreService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditStorePopupController {

    @FXML private TextField nameField;
    @FXML private TextField locationField;

    @FXML private Text errorText;
    @FXML private HBox errorBox;

    private Store currentStore;

    /**
     * Initialise les données du magasin à modifier.
     * @param store Le magasin à modifier.
     */
    public void initStoreData(Store store) {
        this.currentStore = store;
        nameField.setText(store.getName());
        locationField.setText(store.getLocation());
    }

    /**
     * Met à jour les informations du magasin.
     */
    @FXML
    private void handleUpdateStore() {
        errorBox.setVisible(false);
        String name = nameField.getText();
        String location = locationField.getText();

        if (name.isEmpty() || location.isEmpty()) {
            errorText.setText("Veuillez remplir tous les champs.");
            errorBox.setVisible(true);
            return;
        }

        try {
            StoreService storeService = Application.getStoreService();
            Store updatedStore = new Store(currentStore.getId(), name, location, currentStore.getEmployees());
            System.out.println(location);
            storeService.updateStore(updatedStore);
            closePopup();
        } catch (SQLException e) {
            errorText.setText("Erreur lors de la mise à jour du magasin.");
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