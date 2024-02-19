package com.istore.services;

import com.istore.dao.InventoryDAO;
import com.istore.models.Inventory;

import java.sql.SQLException;

public class InventoryService {

    private InventoryDAO inventoryDAO;

    public InventoryService(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    /**
     * Récupère l'inventaire par l'id d'un magasin.
     * @param storeId l'id du magasin
     * @return l'id de l'inventaire
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public int getInventoryIdByStoreId(int storeId) throws SQLException {
        return inventoryDAO.getInventoryIdByStoreId(storeId);
    }
}
