package Exceptions;

import Field.User;

public class InvalidUsernameException extends RequestFailedException {
    User user;

    public InvalidUsernameException() {

    }

    public InvalidUsernameException(User user) {
        this.user = user;
    }
}
//        this.user.credential.usrName = user.credential.usrName;