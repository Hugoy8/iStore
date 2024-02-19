package com.istore.gui.controllers.dashboard.popup.items;

import com.istore.Application;
import com.istore.models.Item;
import com.istore.services.ItemService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditItemPopupController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;

    @FXML private Text errorText;
    @FXML private HBox errorBox;

    private Item currentItem;

    /**
     * Initialise les données de l'item à modifier.
     * @param item L'item à modifier.
     */
    public void initItemData(Item item) {
        this.currentItem = item;
        nameField.setText(item.getName());
        priceField.setText(String.valueOf(item.getPrice()));
        stockField.setText(String.valueOf(item.getStock()));
    }

    /**
     * Met à jour les informations de l'utilisateur.
     */
    @FXML
    private void handleUpdateItem() {
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
            // Convertir les valeurs en types appropriés
            double price = Double.parseDouble(priceAsString);
            int stock = Integer.parseInt(stockAsString);

            ItemService itemService = Application.getItemService();
            Item updatedItem = new Item(currentItem.getId(), name, price, stock, currentItem.getInventoryId());
            itemService.updateItem(updatedItem);
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
     * Ferme la popup.
     */
    private void closePopup() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
