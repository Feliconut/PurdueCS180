package Exceptions;

public class RequestParsingException extends RequestFailedException {
    public RequestParsingException() {
    }

    public RequestParsingException(String message) {
        super(message);
    }
}
