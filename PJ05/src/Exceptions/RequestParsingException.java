package Exceptions;

public class RequestParsingException extends Exception
{
    public RequestParsingException()
    {
    }

    public RequestParsingException(String message)
    {
        super(message);
    }
}
