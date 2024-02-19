package com.istore.services;

import com.istore.dao.UserDAO;
import com.istore.dao.WhitelistEmailDAO;
import com.istore.models.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDAO userDAO;
    private WhitelistEmailDAO whitelistEmailDAO;

    public UserService(UserDAO userDAO, WhitelistEmailDAO whitelistEmailDAO) {
        this.userDAO = userDAO;
        this.whitelistEmailDAO = whitelistEmailDAO;
    }

    /**
     * Crée un nouvel utilisateur.
     * @param user L'utilisateur à créer.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void createUser(User user) throws SQLException {
        userDAO.createUser(user);
    }

    /**
     * Met à jour les informations d'un utilisateur.
     * @param user L'utilisateur avec les informations mises à jour.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }

    /**
     * Supprime un utilisateur par son identifiant.
     * @param userId L'identifiant de l'utilisateur à supprimer.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void deleteUserById(int userId) throws SQLException {
        userDAO.deleteUserById(userId);
    }

    /**
     * Liste tous les utilisateurs.
     * @return La liste de tous les utilisateurs.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<User> listAllUsers() throws SQLException {
        return userDAO.listAllUsers();
    }

    /**
     * Permet de vérifier si un utilisateur est un admin.
     * @param user L'utilisateur à vérifier
     * @return Retourne True si l'utilisateur est un admin sinon False.
     */
    public boolean checkAdmin(User user){
        return user.getRole().equals("admin");
    }

    /**
     * Ajoute une adresse e-mail à la whitelist.
     * @param email L'adresse e-mail à ajouter.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void addEmailToWhitelist(String email) throws SQLException {
        whitelistEmailDAO.addEmailToWhitelist(email);
    }
}
