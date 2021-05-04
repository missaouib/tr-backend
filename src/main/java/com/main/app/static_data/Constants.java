package com.main.app.static_data;

public class Constants {

    // minimum 8 characters, at least one uppercase letter, one lowercase letter, one number and one special character
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    public static final String URL_PART_USER = "/userConfirmation";
    public static final String URL_PASSWORD_RESET = "/resetPassword";
    public static final String URL_COMMENT_ID = "/comment/details";

    public static final int VALIDITY_OF_TOKEN_IN_DAYS = 5;

}
