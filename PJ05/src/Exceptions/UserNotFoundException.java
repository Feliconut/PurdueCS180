package Exceptions;

import Field.User;

public class UserNotFoundException extends RequestFailedException {
    User user;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(User user) {
        this.user = user;
    }
}
