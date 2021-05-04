package com.main.app.util;

import com.main.app.static_data.Constants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

public class UserUtil {

    public static final int PASSWORD_MIN_LENGTH = 6;

    public static final int PASSWORD_MAX_LENGTH = 100;


    public static boolean validatePassword(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= PASSWORD_MIN_LENGTH &&
                password.length() <= PASSWORD_MAX_LENGTH &&
                password.matches(Constants.PASSWORD_REGEX);
    }

    public static String encryptUserPassword(String password) {
        return new BCryptPasswordEncoder().encode(String.valueOf(password));
    }

    public static boolean validateEmail(String email) {
        return email.matches(Constants.EMAIL_REGEX);
    }

}
