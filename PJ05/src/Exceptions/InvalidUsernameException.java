package Exceptions;


public class InvalidUsernameException extends RequestFailedException {

    public InvalidUsernameException() {

    }

    public InvalidUsernameException(String message) {
        super(message);
    }
}

