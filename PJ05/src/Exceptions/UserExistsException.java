package Exceptions;

public class UserExistsException extends RequestFailedException {


    public UserExistsException() {
    }

    public UserExistsException(String message) {
        super(message);
    }


}
