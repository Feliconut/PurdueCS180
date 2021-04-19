package Exceptions;

import Request.Request;

public class InvalidRequestException extends RequestFailedException {
    Request request;

    public InvalidRequestException() {
    }

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(Request request)
    {
        this.request = request;
    }
}
