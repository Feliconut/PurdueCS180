package Request;

import Field.Credential;

public class DeleteAccountRequest extends Request{
    public final Credential credential;


    public DeleteAccountRequest(Credential credential) {
        super();
        this.credential = credential;
    }
}
