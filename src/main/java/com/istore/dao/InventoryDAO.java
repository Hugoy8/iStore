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
     * Récupère l'inventaire par l'id d'un magasin.
     * @param storeId l'id du magasin
     * @return l'id de l'inventaire
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public int getInventoryIdByStoreId(int storeId) throws SQLException {
        String query = "SELECT id FROM inventories WHERE store_id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, storeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Aucun inventaire trouvé pour le storeId: " + storeId);
            }
        }
    }

}
