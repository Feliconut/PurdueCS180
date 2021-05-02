package Exceptions;

/**
 * Project5-- UserNotFoundException
 * <p>
 * This exception will be called if the system can't find the user that you are looking for
 *
 * @author team 84
 * @version 04/30/2021
 */
public class UserNotFoundException extends RequestFailedException {


    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
