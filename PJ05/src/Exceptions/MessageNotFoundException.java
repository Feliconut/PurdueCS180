package Exceptions;


public class MessageNotFoundException extends RequestFailedException {


    public MessageNotFoundException() {
    }

    public MessageNotFoundException(String message) {
        super(message);
    }


}
