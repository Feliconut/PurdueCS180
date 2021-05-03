package Request;

import java.util.UUID;

/**
 * Project5-- EditMessageRequest
 * <p>
 * It is the request to edit a message
 *
 * @author team 84
 * @version 04/30/2021
 */
public class EditMessageRequest extends Request {

    public final UUID messsageUUID;
    public final String content;


    public EditMessageRequest(UUID messsageUUID, String content) {
        super();
        this.messsageUUID = messsageUUID;
        this.content = content;

    }

    @Override
    public String toString() {
        return "EditMessageRequest{" + "messsageUUID=" + messsageUUID + ", content='" + content + '\'' + ", uuid=" +
                uuid + '}';
    }
}
