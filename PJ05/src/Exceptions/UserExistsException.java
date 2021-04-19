package Exceptions;

import Field.User;

public class UserExistsException extends RequestFailedException {
    User user;

    public UserExistsException() {
    }

    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException(User user) {
        this.user = user;
    }
}
