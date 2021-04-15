package Request;

import Field.Credential;

public class AuthenticateRequest extends BaseClientRequest
{
    public final Credential credential;

    public AuthenticateRequest(Credential credential)
    {
        this.credential = credential;
    }

    public static AuthenticateRequest parseRequest(String requestStr) throws RequestParsingException
    {
        //TODO
        return null;
    }

    @Override
    public String toString()
    {
        return null;
    }
}
