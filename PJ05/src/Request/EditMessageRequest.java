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

    public final UUID messsage_uuid;
    public final String content;


    public EditMessageRequest(UUID messsage_uuid, String content) {
        super();
        this.messsage_uuid = messsage_uuid;
        this.content = content;

    }

    @Override
    public String toString() {
        return "EditMessageRequest{" +
                "messsage_uuid=" + messsage_uuid +
                ", content='" + content + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
