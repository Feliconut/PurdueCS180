package Exceptions;

public class RequestFailedException extends Exception {

    public RequestFailedException() {
    }

    public RequestFailedException(String message) {
        super(message);
    }
}
