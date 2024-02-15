package com.istore.services;

import com.istore.dao.UserDAO;
import com.istore.models.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
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
}
