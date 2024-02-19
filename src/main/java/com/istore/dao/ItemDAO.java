package com.istore.dao;

import com.istore.database.Database;
import com.istore.models.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private Database db;

    public ItemDAO(Database db) {
        this.db = db;
    }

    /**
     * Créer un item
     * @param item Item à créer
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void createItem(Item item) throws SQLException {
        String query = "INSERT INTO items (name, price, stock, inventory_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getStock());
            ps.setInt(4, item.getInventoryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Mettre à jour un item
     * @param item Item à mettre à jour
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void updateItem(Item item) throws SQLException {
        String query = "UPDATE items SET name = ?, price = ?, stock = ?, inventory_id = ? WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getStock());
            ps.setInt(4, item.getInventoryId());
            ps.setInt(5, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Supprimer un item par son id
     * @param id ID de l'item
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void deleteItem(int id) throws SQLException {
        String query = "DELETE FROM items WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrouver des items par leur id de magasin
     * @param storeId ID du magasin
     * @return items Liste des items
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<Item> findItemsByStoreId(int storeId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE inventory_id IN (SELECT id FROM inventories WHERE store_id = ?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, storeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("inventory_id")
                ));
            }
        }
        return items;
    }

    /**
     * Incrémenter le stock d'un item
     * @param itemId ID de l'item
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void incrementStock(int itemId) throws SQLException {
        String query = "UPDATE items SET stock = stock + 1 WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, itemId);
            ps.executeUpdate();
        }
    }

    /**
     * Décrémenter le stock d'un item
     * @param itemId ID de l'item
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void decrementStock(int itemId) throws SQLException {
        String query = "UPDATE items SET stock = CASE WHEN stock > 0 THEN stock - 1 ELSE 0 END WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, itemId);
            ps.executeUpdate();
        }
    }

}
