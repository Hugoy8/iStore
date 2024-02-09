package com.istore.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    // Validation d'une adresse email
    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
