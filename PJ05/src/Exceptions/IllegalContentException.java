package Exceptions;

public class IllegalContentException extends RequestFailedException {
    public IllegalContentException() {
    }

    public IllegalContentException(String message) {
        super(message);
    }
}
