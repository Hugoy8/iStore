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
     * @param storeId ID du magasin
     * @return Inventory
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public Inventory findInventoryByStoreId(int storeId) throws SQLException {
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
            throw e;
        }
        return null;
    }

    /**
     * Créer un inventaire
     * @param inventory Inventaire à créer
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void createInventory(Inventory inventory) throws SQLException {
        String query = "INSERT INTO inventories (store_id) VALUES (?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, inventory.getStoreId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Mettre à jour un inventaire par son id de magasin
     * @param storeId ID du magasin
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void deleteInventoryByStoreId(int storeId) throws SQLException {
        String query = "DELETE FROM inventories WHERE store_id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, storeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Lister tous les inventaires disponibles
     * @return Liste des inventaires
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<Inventory> listAllInventories() throws SQLException {
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
            throw e;
        }
        return inventories;
    }
}
