package com.istore.services;

import com.istore.dao.UserDAO;
import com.istore.models.User;
import com.istore.utils.HashUtil;

public class AuthService {

    private UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Vérifie les identifiants de connexion de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @return Le User si les identifiants sont corrects, sinon null.
     */
    public User login(String email, String password) {
        User user = userDAO.findUserByEmail(email);
        if (user != null && HashUtil.checkPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    /**
     * Enregistre un nouvel utilisateur dans le système.
     * @param user L'utilisateur à enregistrer.
     */
    public void register(User user) {
        String hashedPassword = HashUtil.hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        userDAO.createUser(user);
    }
}
