package Exceptions;

/**
 * Project5-- AuthorizationException
 * <p>
 * This exception will be called if the system find your provided information is incorrect
 *
 * @author team 84
 * @version 04/30/2021
 */
public class AuthorizationException extends RequestFailedException {
    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
