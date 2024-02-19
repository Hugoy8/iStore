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
        String query = "INSERT INTO items (name, price, quantity, inventory_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getQuantity());
            ps.setInt(4, item.getInventoryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrouver un item par son id
     * @param id ID de l'item
     * @return Item
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public Item findItemById(int id) throws SQLException {
        String query = "SELECT * FROM items WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("inventory_id")
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    /**
     * Mettre à jour un item
     * @param item Item à mettre à jour
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void updateItem(Item item) throws SQLException {
        String query = "UPDATE items SET name = ?, price = ?, quantity = ?, inventory_id = ? WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getQuantity());
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
     * Retrouver tous les items
     * @return Liste des items
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<Item> listAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items";
        try (Connection conn = db.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("inventory_id")
                ));
            }
        } catch (SQLException e) {
            throw e;
        }
        return items;
    }
}
