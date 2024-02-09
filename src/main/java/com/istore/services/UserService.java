package com.istore.services;

import com.istore.dao.UserDAO;
import com.istore.models.User;

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
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    /**
     * Crée un nouvel utilisateur.
     * @param user L'utilisateur à créer.
     */
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    /**
     * Met à jour les informations d'un utilisateur.
     * @param user L'utilisateur avec les informations mises à jour.
     */
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    /**
     * Supprime un utilisateur.
     * @param userId L'identifiant de l'utilisateur à supprimer.
     */
    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

    /**
     * Liste tous les utilisateurs.
     * @return La liste de tous les utilisateurs.
     */
    public List<User> listAllUsers() {
        return userDAO.listAllUsers();
    }
}
