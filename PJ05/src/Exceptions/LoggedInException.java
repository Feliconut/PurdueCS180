package Exceptions;

/**
 * Project5-- LoggedInException
 * <p>
 * This exception will be called if the system find you already logged in.
 *
 * @author team 84
 * @version 04/30/2021
 */
public class LoggedInException extends RequestFailedException {
    public LoggedInException() {
    }

    public LoggedInException(String message) {
        super(message);
    }
}
