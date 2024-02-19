package com.istore.services;

import com.istore.dao.ItemDAO;
import com.istore.models.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    /**
     * Récupère un item par son id de magasin.
     * @param storeId
     * @return L'item trouvé.
     * @throws SQLException
     */
    public List<Item> findItemsByStoreId(int storeId) throws SQLException {
        return itemDAO.findItemsByStoreId(storeId);
    }

    /**
     * Met à jour un item.
     * @param item L'item à mettre à jour.
     * @throws SQLException
     */
    public void updateItem(Item item) throws SQLException {
        itemDAO.updateItem(item);
    }

    /**
     * Supprime un item par son id.
     * @param itemId L'id de l'item à supprimer.
     * @throws SQLException
     */
    public void deleteItemById(int itemId) throws SQLException {
        itemDAO.deleteItem(itemId);
    }
}
