package com.istore.dao;


import com.istore.database.Database;
import com.istore.models.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    private Database db;

    public InventoryDAO(Database db) {
        this.db = db;
    }

    /**
     * Retrouver un inventaire par son id de magasin
     * @param storeId
     * @return Inventory
     */
    public Inventory findInventoryByStoreId(int storeId) {
        String query = "SELECT * FROM inventories WHERE store_id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, storeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Inventory(
                        rs.getInt("store_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Créer un inventaire
     * @param inventory
     */
    public void createInventory(Inventory inventory) {
        String query = "INSERT INTO inventories (store_id) VALUES (?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, inventory.getStoreId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mettre à jour un inventaire par son id de magasin
     * @param storeId
     */
    public void deleteInventoryByStoreId(int storeId) {
        String query = "DELETE FROM inventories WHERE store_id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, storeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lister tous les inventaires disponibles
     * @return List<Inventory>
     */
    public List<Inventory> listAllInventories() {
        List<Inventory> inventories = new ArrayList<>();
        String query = "SELECT * FROM inventories";
        try (Connection conn = db.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                inventories.add(new Inventory(
                        rs.getInt("store_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventories;
    }
}
