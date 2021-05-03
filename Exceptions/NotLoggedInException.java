package Exceptions;

/**
 * Project5-- NotLoggedInException
 * <p>
 * This exception will be called if you didn't logged in
 *
 * @author team 84
 * @version 04/30/2021
 */
public class NotLoggedInException extends RequestFailedException {
    public NotLoggedInException() {
    }

    public NotLoggedInException(String message) {
        super(message);
    }
}
