package Exceptions;

import Field.User;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(){}

    public InvalidPasswordException(String message){
        super(message);
    }

}
