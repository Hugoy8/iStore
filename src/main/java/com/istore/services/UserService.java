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
     * Récupère un utilisateur par son email.
     * @param email L'email de l'utilisateur à récupérer.
     * @return L'utilisateur correspondant à l'email.
     */
    public User getUserByEmail(String email) throws SQLException {
        return userDAO.findUserByEmail(email);
    }

    /**
     * Crée un nouvel utilisateur.
     * @param user L'utilisateur à créer.
     */
    public void createUser(User user) throws SQLException {
        userDAO.createUser(user);
    }

    /**
     * Met à jour les informations d'un utilisateur.
     * @param user L'utilisateur avec les informations mises à jour.
     */
    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }

    /**
     * Supprime un utilisateur par son identifiant.
     * @param userId L'identifiant de l'utilisateur à supprimer.
     */
    public void deleteUserById(int userId) throws SQLException {
        userDAO.deleteUserById(userId);
    }

    /**
     * Supprime un utilisateur.
     * @param userDeleteId L'identifiant de l'utilisateur à supprimer.
     * @param userAction L'utilisateur qui effectue l'action.
     */
    public void deleteUser(int userDeleteId, User userAction) throws SQLException {
        userDAO.deleteUser(userDeleteId, userAction);
    }

    /**
     * Liste tous les utilisateurs.
     * @return La liste de tous les utilisateurs.
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
     *
     * @param email L'adresse e-mail à ajouter.
     * @throws SQLException Si l'ajout échoue en raison d'une contrainte de base de données ou d'une autre erreur SQL.
     */
    public void addEmailToWhitelist(String email) throws SQLException {
        whitelistEmailDAO.addEmailToWhitelist(email);
    }
}
