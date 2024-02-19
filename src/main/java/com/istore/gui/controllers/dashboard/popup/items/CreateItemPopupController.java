package com.istore.gui.controllers.dashboard.popup.items;

import com.istore.Application;
import com.istore.models.Item;
import com.istore.models.Store;
import com.istore.models.User;
import com.istore.services.ItemService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateItemPopupController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML private Text errorText;
    @FXML private HBox errorBox;

    // ID de l'inventaire actuel
    private int inventoryId;

    /**
     * Définit l'ID de l'inventaire pour le nouvel item.
     * @param inventoryId L'ID de l'inventaire.
     */
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * Crée un utilisateur.
     */
    @FXML
    private void handleCreateUser() {
        errorBox.setVisible(false);

        String name = nameField.getText();
        String priceAsString = priceField.getText();
        String stockAsString = stockField.getText();

        if (name.isEmpty() || priceAsString.isEmpty() || stockAsString.isEmpty()) {
            errorText.setText("Veuillez remplir tous les champs.");
            errorBox.setVisible(true);
            return;
        }

        try {
            double price = Double.parseDouble(priceAsString);
            int stock = Integer.parseInt(stockAsString);

            ItemService itemService = Application.getItemService();
            Item newItem = new Item(0, name, price, stock, inventoryId);
            itemService.createItem(newItem);
            closePopup();
        } catch (NumberFormatException e) {
            errorText.setText("Le prix et le stock doivent être des nombres valides.");
            errorBox.setVisible(true);
        } catch (SQLException e) {
            errorText.setText("Erreur lors de la mise à jour de l'article.");
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
