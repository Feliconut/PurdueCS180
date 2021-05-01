package Exceptions;

/**
 * Project5-- InvalidConversationNameException
 * <p>
 * This exception will be called if the system find your provided conversation_name contain some special character
 *
 * @author team 84
 * @version 04/30/2021
 */
public class InvalidConversationNameException extends RequestFailedException {
    public InvalidConversationNameException() {
    }

    public InvalidConversationNameException(String message) {
        super(message);
    }
}
