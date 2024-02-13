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
     * @param store Le magasin à créer
     */
    public void createStore(Store store) throws SQLException {
        String query = "INSERT INTO stores (name) VALUES (?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, store.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrouver un magasin par son id
     * @param id
     * @return Store
     */
    public Store findStoreById(int id) throws SQLException {
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
            throw e;
        }
        return null;
    }

    /**
     * Mettre à jour un magasin par son id
     * @param store
     */
    public void updateStore(Store store) throws SQLException {
        String query = "UPDATE stores SET name = ? WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, store.getName());
            ps.setInt(2, store.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Supprimer un magasin par son id
     * @param id
     */
    public void deleteStore(int id) throws SQLException {
        String query = "DELETE FROM stores WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrouver tous les magasins disponibles
     * @return List<Store>
     */
    public List<Store> listAllStores() throws SQLException {
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
            throw e;
        }
        return stores;
    }
}
