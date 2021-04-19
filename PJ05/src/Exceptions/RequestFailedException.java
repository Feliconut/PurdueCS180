package Exceptions;

import Request.Request;

public class RequestFailedException extends Exception {
    Request request;

    public RequestFailedException() {
    }

    public RequestFailedException(String message) {
        super(message);
    }

    public RequestFailedException(Request request) {
        this.request = request;
    }
}
