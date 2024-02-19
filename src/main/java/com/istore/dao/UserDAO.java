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
     * @param email L'adresse mail de l'utilisateur
     * @return User
     * @throws SQLException
     */
    public User findUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
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
            throw e;
        }
        return null;
    }

    /**
     * Retrouver un utilisateur par son id
     * @param id L'id de l'utilisateur
     * @return User
     * @throws SQLException
     */
    public User findUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = this.db.getConnectionDb();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("pseudo"),
                            rs.getString("password_hash"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    /**
     * Créer un utilisateur
     * @param user L'utilisateur à créer
     * @throws SQLException
     */
    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (email, pseudo, password_hash, role) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPseudo());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Mettre à jour un utilisateur complet
     * @param user L'utilisateur à mettre à jour
     * @throws SQLException
     */
    public void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET email = ?, pseudo = ?, password_hash = ?, role = ? WHERE id = ?";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPseudo());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Supprimer un utilisateur par son id
     * @param userId L'id de l'utilisateur à supprimer
     * @throws SQLException
     */
    public void deleteUserById(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = this.db.getConnectionDb();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Supprimer un utilisateur par son id
     * @param userDeleteId L'id de l'utilisateur à supprimer
     * @param userAction L'utilisateur qui effectue l'action
     * @throws SQLException
     */
    public void deleteUser(int userDeleteId, User userAction) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userDeleteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Récupérer la liste de tous les utilisateurs
     * @return List<User> Liste des utilisateurs
     * @throws SQLException
     */
    public List<User> listAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            Connection connection = this.db.getConnectionDb();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
            throw e;
        }
        return users;
    }


    /**
     * Permet de vérifier si un pseudo est déjà utilisé
     * @param pseudo Le pseudo à vérifier
     * @return Retourne True si le pseudo est libre sinon False.
     * @throws SQLException
     */
    public boolean checkPseudo(String pseudo) throws SQLException {
        String query = "SELECT * FROM users WHERE pseudo = ?";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pseudo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            throw e;
        }

        return true;
    }

    /**
     * Permet de vérifier si un email est déjà utilisé
     * @param email L'email à vérifier
     * @return Retourne True si le pseudo est libre sinon False.
     * @throws SQLException
     */
    public boolean checkEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            throw e;
        }

        return true;
    }

    /**
     * Permet de vérifier si un email est sur la white list
     * @param email L'email à vérifier
     * @return Retourne True si l'email est sur la white list sinon False.
     * @throws SQLException
     */
    public boolean checkWhiteList(String email) throws SQLException {
        String query = "SELECT * FROM whitelist_emails WHERE email = ?";
        try {
            Connection connection = this.db.getConnectionDb();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw e;
        }

        return false;
    }
}
