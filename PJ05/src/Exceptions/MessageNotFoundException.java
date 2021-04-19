package Exceptions;

import Field.Message;

public class MessageNotFoundException extends RequestFailedException {
    Message message;

    public MessageNotFoundException() {
    }

    public MessageNotFoundException(String message) {
        super(message);
    }

    public MessageNotFoundException(Message message) {
        this.message = message;
    }

}
