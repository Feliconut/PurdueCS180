package Exceptions;

/**
 * Project5-- InvalidPasswordException
 * <p>
 * This exception will be called if the system find your provided password could not be found
 *
 * @author team 84
 * @version 04/30/2021
 */
public class InvalidPasswordException extends RequestFailedException {

    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

}
