package com.istore.dao;

import com.istore.database.Database;
import com.istore.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Database db;

    public UserDAO(Database db) {
        this.db = db;
    }

    /**
     * Retrouver un utilisateur par son adresse mail
     * @param email
     * @return User
     */
    public User findUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("pseudo"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Créer un utilisateur
     * @param user
     */
    public void createUser(User user) {
        String query = "INSERT INTO users (email, pseudo, password_hash, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPseudo());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mettre à jour un utilisateur complet
     * @param user
     */
    public void updateUser(User user) {
        String query = "UPDATE users SET email = ?, pseudo = ?, password_hash = ?, role = ? WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPseudo());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprimer un utilisateur par son id
     * @param userId
     */
    public void deleteUser(int userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = db.getConnectionDb();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupérer la liste de tous les utilisateurs
     * @return List<User>
     */
    public List<User> listAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = db.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("pseudo"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
