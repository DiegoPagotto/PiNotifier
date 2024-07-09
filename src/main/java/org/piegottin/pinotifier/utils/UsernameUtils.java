package org.piegottin.pinotifier.utils;

public class UsernameUtils {

    public static boolean isValidNick(String username) {
        if (username.length() < 3 || username.length() > 16) {
            return false;
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return false;
        }

        if (username.matches("^_{2,}") || username.matches("_{2,}$")) {
            return false;
        }

        return true;
    }
}
