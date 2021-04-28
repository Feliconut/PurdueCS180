package Exceptions;

public class LoggedInException extends RequestFailedException {
    public LoggedInException() {
    }

    public LoggedInException(String message) {
        super(message);
    }
}
