package com.istore.utils;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

    // Permet de hasher un mot de passe
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Permet de vérifier un mot de passe par rapport à un hash
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
