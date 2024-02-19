package com.istore.dao;

import com.istore.database.Database;
import com.istore.models.Store;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        String query = "INSERT INTO stores (name, location) VALUES (?, ?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, store.getName());
            ps.setString(2, store.getLocation());
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
                List<String> employees = new ArrayList<>();
                return new Store(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        employees
                );
            }
        }
        return null;
    }

    /**
     * Mettre à jour un magasin par son id
     * @param store
     */
    public void updateStore(Store store) throws SQLException {
        String query = "UPDATE stores SET name = ?, location = ? WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, store.getName());
            ps.setString(2, store.getLocation());
            ps.setInt(3, store.getId());
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
    public List<Store> listAllStoresWithEmployees() throws SQLException {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT s.id, s.name, s.location, GROUP_CONCAT(u.pseudo SEPARATOR ', ') AS employees " +
                "FROM stores s " +
                "LEFT JOIN store_employees se ON s.id = se.store_id " +
                "LEFT JOIN users u ON se.user_id = u.id " +
                "GROUP BY s.id";
        try (Connection conn = db.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Store store = new Store(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("employees") != null ? List.of(rs.getString("employees").split(", ")) : List.of()
                );
                stores.add(store);
            }
        }
        return stores;
    }
}

