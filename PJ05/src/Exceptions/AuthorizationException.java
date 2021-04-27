package Exceptions;

public class AuthorizationException extends RequestFailedException {
    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
