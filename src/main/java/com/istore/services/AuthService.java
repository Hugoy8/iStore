package com.istore.services;

import com.istore.dao.UserDAO;
import com.istore.gui.AppLauncher;
import com.istore.models.User;
import com.istore.utils.HashUtil;

import java.io.IOException;
import java.sql.SQLException;

public class AuthService {

    private UserDAO userDAO;

    private User user = null;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Vérifie les identifiants de connexion de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @return Le User si les identifiants sont corrects, sinon null.
     */
    public User login(String email, String password) throws SQLException {
        User user = userDAO.findUserByEmail(email);

        if (user != null && HashUtil.checkPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    /**
     * Enregistre un nouvel utilisateur dans le système.
     * @param user L'utilisateur à enregistrer.
     * @return Un message d'erreur ou de succès lors de l'inscription.
     */
    public String register(User user) throws SQLException {
        // Vérification de l'unicité de l'email et du pseudo
        if (!this.userDAO.checkEmail(user.getEmail())) {
            return "L'adresse email est déjà utilisé.";
        }
        if (!this.userDAO.checkPseudo(user.getPseudo())) {
            return "Ce pseudo est déjà utilisé.";
        }
        if (!this.userDAO.checkWhiteList(user.getEmail())) {
            return "L'adresse email n'est pas autorisé à s'inscrire.";
        }

        String hashedPassword = HashUtil.hashPassword(user.getPasswordHash());

        if (hashedPassword == null) {
            return "Erreur lors du hashage du mot de passe. Veuillez ressayer plus tard.";
        }

        user.setPasswordHash(hashedPassword);
        try {
            userDAO.createUser(user);
        } catch (Exception e) {
            return "Erreur durant la création de l'utilisateur. Veuillez ressayer plus tard.";
        }

        return "Utilisateur enregistré avec succès.";
    }

    public User getUser() throws IOException {
        if (user == null){
            AppLauncher.showLoginView();
        }
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
