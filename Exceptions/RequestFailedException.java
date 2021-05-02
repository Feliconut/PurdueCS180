package Exceptions;

/**
 * Project5-- RequestFailedException
 * <p>
 * This exception could be called anyway, anything incorrect could be conclude as requestFailed.
 *
 * @author team 84
 * @version 04/30/2021
 */
public class RequestFailedException extends Exception {

    public RequestFailedException() {
    }

    public RequestFailedException(String message) {
        super(message);
    }
}
