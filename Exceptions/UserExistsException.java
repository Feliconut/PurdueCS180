package Exceptions;

/**
 * Project5-- UserExistsException
 * <p>
 * This exception will be called when the user create their account but the username already exists
 *
 * @author team 84
 * @version 04/30/2021
 */
public class UserExistsException extends RequestFailedException {


    public UserExistsException() {
    }

    public UserExistsException(String message) {
        super(message);
    }


}
