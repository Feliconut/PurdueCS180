package Exceptions;

public class InvalidConversationNameException extends RequestFailedException {
    public InvalidConversationNameException() {
    }

    public InvalidConversationNameException(String message) {
        super(message);
    }
}
