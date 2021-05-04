package com.main.app.exception;

/**
 * Custom made exception thrown when user tries to reset password with non existing or expired token.
 *
 *
 * @author Nikola
 *
 */
public class TokenExpiredException extends IllegalArgumentException {

    private static final String MESSAGE = "TOKEN_EXPIRED";

    public TokenExpiredException() {
        super(MESSAGE);
    }
}
