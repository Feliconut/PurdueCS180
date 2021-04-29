package Exceptions;

public class NotLoggedInException extends RequestFailedException {
    public NotLoggedInException() {
    }

    public NotLoggedInException(String message) {
        super(message);
    }
}
