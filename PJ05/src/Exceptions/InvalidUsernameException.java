package Exceptions;

/**
 * Project5-- InvalidUsernameException
 * <p>
 * This exception will be called if the system find your provided username could not be found
 *
 * @author team 84
 * @version 04/30/2021
 */
public class InvalidUsernameException extends RequestFailedException {

    public InvalidUsernameException() {

    }

    public InvalidUsernameException(String message) {
        super(message);
    }
}

