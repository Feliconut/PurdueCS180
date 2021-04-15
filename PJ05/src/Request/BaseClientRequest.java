package Request;

public abstract class BaseClientRequest
{
    public BaseClientRequest()
    {

    }

    public static BaseClientRequest parseRequest(String requestStr) throws RequestParsingException
    {
        return null;
    }

    public abstract String toString();


}

