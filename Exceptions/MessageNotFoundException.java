package Exceptions;

/**
 * Project5-- MessageNotFoundException
 * <p>
 * This exception will be called if the system find your provided message could not be found in somewhere like a
 * conversation
 *
 * @author team 84
 * @version 04/30/2021
 */

public class MessageNotFoundException extends RequestFailedException {


    public MessageNotFoundException() {
    }

    public MessageNotFoundException(String message) {
        super(message);
    }


}
