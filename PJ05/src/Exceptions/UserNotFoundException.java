package Exceptions;

public class UserNotFoundException extends RequestFailedException {


    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
