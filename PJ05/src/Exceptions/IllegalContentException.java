package Exceptions;

/**
 * Project5-- IllegalContentException
 * <p>
 * This exception will be called if the system find your provided content contain some special character
 *
 * @author team 84
 * @version 04/30/2021
 */
public class IllegalContentException extends RequestFailedException {
    public IllegalContentException() {
    }

    public IllegalContentException(String message) {
        super(message);
    }
}
