package Exceptions;

/**
 * Project5-- ConversationNotFoundException
 * <p>
 * This exception will be called if the system find your provided conversationUUID could not be found
 *
 * @author team 84
 * @version 04/30/2021
 */
public class ConversationNotFoundException extends RequestFailedException {


    public ConversationNotFoundException() {
    }

    public ConversationNotFoundException(String message) {
        super(message);
    }


}
