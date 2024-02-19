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
     * Crée un item.
     * @param item L'item à créer.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void createItem(Item item) throws SQLException {
        itemDAO.createItem(item);
    }

    /**
     * Récupère un item par son id de magasin.
     * @param storeId
     * @return L'item trouvé.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<Item> findItemsByStoreId(int storeId) throws SQLException {
        return itemDAO.findItemsByStoreId(storeId);
    }

    /**
     * Met à jour un item.
     * @param item L'item à mettre à jour.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void updateItem(Item item) throws SQLException {
        itemDAO.updateItem(item);
    }

    /**
     * Supprime un item par son id.
     * @param itemId L'id de l'item à supprimer.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void deleteItemById(int itemId) throws SQLException {
        itemDAO.deleteItem(itemId);
    }

    /**
     * Incrémenter le stock d'un item.
     * @param itemId
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void incrementStock(int itemId) throws SQLException {
        itemDAO.incrementStock(itemId);
    }

    /**
     * Décrémenter le stock d'un item.
     * @param itemId
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void decrementStock(int itemId) throws SQLException {
        itemDAO.decrementStock(itemId);
    }

}
