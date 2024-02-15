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

    /**
     * Met à jour le mot de passe d'un utilisateur.
     * @param userId L'identifiant de l'utilisateur.
     * @param newPassword Le nouveau mot de passe de l'utilisateur.
     * @return Un message indiquant si la mise à jour a réussi ou non.
     */
    public String updateUserPassword(int userId, String newPassword) throws SQLException {
        // Hasher le nouveau mot de passe
        String hashedPassword = HashUtil.hashPassword(newPassword);
        if (hashedPassword == null) {
            return "Erreur lors du hashage du mot de passe. Veuillez réessayer plus tard.";
        }

        // Récupérer l'utilisateur par son ID
        User user = userDAO.findUserById(userId);
        if (user == null) {
            return "Utilisateur introuvable.";
        }

        // Mettre à jour le mot de passe hashé de l'utilisateur
        user.setPasswordHash(hashedPassword);
        try {
            userDAO.updateUser(user);
            return "Mot de passe mis à jour avec succès.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la mise à jour du mot de passe. Veuillez réessayer plus tard.";
        }
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
