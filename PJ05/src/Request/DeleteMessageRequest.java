package Request;

import Field.Credential;

public class DeleteMessageRequest extends Request {

    public final Credential credential;

    public DeleteMessageRequest(Credential credential) {
        super();
        this.credential = credential;
    }


}
