package Exceptions;

public class ConversationNotFoundException extends RequestFailedException {


    public ConversationNotFoundException() {
    }

    public ConversationNotFoundException(String message) {
        super(message);
    }


}
