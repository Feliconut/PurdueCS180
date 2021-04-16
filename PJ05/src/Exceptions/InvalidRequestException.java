package Exceptions;

import Request.Request;

public class InvalidRequestException extends Exception
{
    Request request;

    public InvalidRequestException()
    {
    }

    public InvalidRequestException(String message)
    {
        super(message);
    }

    public InvalidRequestException(Request request)
    {
        this.request = request;
    }
}
