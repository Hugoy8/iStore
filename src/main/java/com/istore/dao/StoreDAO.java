package com.istore.dao;

import com.istore.database.Database;
import com.istore.models.Store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO {

    private Database db;

    public StoreDAO(Database db) {
        this.db = db;
    }

    /**
     * Créer un magasin
     * @param store
     */
    public void createStore(Store store) {
        String query = "INSERT INTO stores (name) VALUES (?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, store.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrouver un magasin par son id
     * @param id
     * @return Store
     */
    public Store findStoreById(int id) {
        String query = "SELECT * FROM stores WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Store(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mettre à jour un magasin par son id
     * @param store
     */
    public void updateStore(Store store) {
        String query = "UPDATE stores SET name = ? WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, store.getName());
            ps.setInt(2, store.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprimer un magasin par son id
     * @param id
     */
    public void deleteStore(int id) {
        String query = "DELETE FROM stores WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrouver tous les magasins disponibles
     * @return List<Store>
     */
    public List<Store> listAllStores() {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT * FROM stores";
        try (Connection conn = db.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                stores.add(new Store(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stores;
    }
}