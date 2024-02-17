package com.istore.dao;

import com.istore.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WhitelistEmailDAO {

    private Database db;

    public WhitelistEmailDAO(Database db) {
        this.db = db;
    }

    /**
     * Ajoute une adresse e-mail à la table whitelist_emails.
     *
     * @param email L'adresse e-mail à ajouter.
     * @throws SQLException Si une erreur de base de données se produit.
     */
    public void addEmailToWhitelist(String email) throws SQLException {
        String query = "INSERT INTO whitelist_emails (email) VALUES (?)";
        try (Connection connection = this.db.getConnectionDb();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Lancer une exception personnalisée si nécessaire, par exemple si l'email existe déjà.
            throw e;
        }
    }
}