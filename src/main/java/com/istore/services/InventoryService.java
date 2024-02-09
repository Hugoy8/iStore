package com.istore.services;

import com.istore.dao.InventoryDAO;
import com.istore.models.Inventory;

public class InventoryService {

    private InventoryDAO inventoryDAO;

    public InventoryService(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    /**
     * Crée un nouvel inventaire pour un magasin.
     * @param inventory L'inventaire à créer.
     */
    public void createInventory(Inventory inventory) {
        inventoryDAO.createInventory(inventory);
    }

    /**
     * Supprime l'inventaire d'un magasin.
     * @param storeId L'identifiant du magasin dont l'inventaire doit être supprimé.
     */
    public void deleteInventory(int storeId) {
        inventoryDAO.deleteInventoryByStoreId(storeId);
    }
}
