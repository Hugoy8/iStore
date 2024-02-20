package com.istore.dao;

import com.istore.database.Database;
import com.istore.models.WhitelistEmail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WhitelistEmailDAO {

    private Database db;

    public WhitelistEmailDAO(Database db) {
        this.db = db;
    }

    /**
     * Ajoute une adresse e-mail à la table whitelist_emails.
     *
     * @param email L'adresse e-mail à ajouter.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données Si une erreur de base de données se produit.
     */
    public void addEmailToWhitelist(String email) throws SQLException {
        String query = "INSERT INTO whitelist_emails (email) VALUES (?)";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Supprime une adresse e-mail de la table whitelist_emails.
     * @param email L'adresse e-mail à supprimer.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données Si une erreur de base de données se produit.
     */
    public void removeEmailFromWhitelist(String email) throws SQLException {
        String query = "DELETE FROM whitelist_emails WHERE email = ?";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Liste les adresses e-mail de la table whitelist_emails.
     * @return La liste des adresses e-mail de la whitelist.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données Si une erreur de base de données se produit.
     */
    public List<WhitelistEmail> listWhitelistEmails() throws SQLException {
        List<WhitelistEmail> emails = new ArrayList<>();
        String query = "SELECT * FROM whitelist_emails";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emails.add(new WhitelistEmail(rs.getInt("id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            throw e;
        }
        return emails;
    }

}