package Exceptions;

public class InvalidPasswordException extends RequestFailedException {

    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

}
