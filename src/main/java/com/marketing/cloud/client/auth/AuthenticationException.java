package com.marketing.cloud.client.auth;

public class AuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 6620413896418260648L;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
