package com.istore.dao;

import com.istore.database.Database;
import com.istore.models.Store;
import com.istore.models.User;

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
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void createStore(Store store) throws SQLException {
        String query = "INSERT INTO stores (name, location) VALUES (?, ?)";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, store.getName());
            ps.setString(2, store.getLocation());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrouver un magasin par son id
     * @param id ID du magasin
     * @return Store
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public Store findStoreById(int id) throws SQLException {
        String query = "SELECT * FROM stores WHERE id = ?";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
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
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    /**
     * Mettre à jour un magasin par son id
     * @param store Le magasin à mettre à jour
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void updateStore(Store store) throws SQLException {
        String query = "UPDATE stores SET name = ?, location = ? WHERE id = ?";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
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
     * @param id ID du magasin
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void deleteStore(int id) throws SQLException {
        String query = "DELETE FROM stores WHERE id = ?";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrouver tous les magasins disponibles
     * @return Liste des magasins
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<Store> listAllStoresWithEmployees() throws SQLException {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT s.id, s.name, s.location, GROUP_CONCAT(u.pseudo SEPARATOR ', ') AS employees " +
                "FROM stores s " +
                "LEFT JOIN store_employees se ON s.id = se.store_id " +
                "LEFT JOIN users u ON se.user_id = u.id " +
                "GROUP BY s.id";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Store store = new Store(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("employees") != null ? List.of(rs.getString("employees").split(", ")) : List.of()
                );
                stores.add(store);
            }
        } catch (SQLException e) {
            throw e;
        }
        return stores;
    }

    /**
     * Retrouver tous les magasins disponibles pour un utilisateur en temps qu'employé.
     * @param user Utilisateur qu'on veut retrouver les magasins
     * @return Liste des magasins
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<Store> listAllStoresWithUserEmployees(User user) throws SQLException {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT s.id, s.name, s.location, GROUP_CONCAT(u.pseudo SEPARATOR ', ') AS employees " +
                "FROM stores s " +
                "JOIN store_employees se ON s.id = se.store_id " +
                "JOIN users u ON se.user_id = u.id " +
                "WHERE s.id IN ( SELECT distinct s.id FROM stores s JOIN store_employees se ON s.id = se.store_id WHERE se.user_id = ? ) " +
                "GROUP BY s.id";
        
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Store store = new Store(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("employees") != null ? List.of(rs.getString("employees").split(", ")) : List.of()
                );
                stores.add(store);
            }
        } catch (SQLException e) {
            throw e;
        }
        return stores;
    }

    /**
     * Retrouver les employés d'un magasin par son id
     * @param storeId ID du magasin
     * @return Liste des employés
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<User> getEmployeesByStoreId(int storeId) throws SQLException {
        List<User> employees = new ArrayList<>();
        String query = "SELECT u.* FROM users u " +
                "JOIN store_employees se ON u.id = se.user_id " +
                "WHERE se.store_id = ?";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, storeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("pseudo"),
                        rs.getString("email"),
                        rs.getString("role")
                );
                employees.add(user);
            }
        } catch (SQLException e) {
            throw e;
        }
        return employees;
    }

    /**
     * Ajouter un employé à un magasin
     * @param storeId ID du magasin
     * @param userId ID de l'employé
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void addEmployeeToStore(int storeId, int userId) throws SQLException {
        String query = "INSERT INTO store_employees (store_id, user_id) VALUES (?, ?)";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, storeId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Supprimer un employé d'un magasin
     * @param storeId ID du magasin
     * @param userId ID de l'employé
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void removeEmployeeFromStore(int storeId, int userId) throws SQLException {
        String query = "DELETE FROM store_employees WHERE store_id = ? AND user_id = ?";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, storeId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Supprimer un employé de tous les magasins
     * @param userId ID de l'employé
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void removeEmployeeFromAllStores(int userId) throws SQLException {
        String query = "DELETE FROM store_employees WHERE user_id = ?";
        try {
            Connection conn = db.getConnectionDb();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}

