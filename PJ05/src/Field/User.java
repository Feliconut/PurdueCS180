package Field;

import java.util.UUID;

public class User extends Storable {
    public Credential credential;
    public Profile profile;

    public User(Credential credential, Profile profile) {
        super(UUID.randomUUID());
        this.credential = credential;
        this.profile = profile;
    }

    public User(UUID uuid, Credential credential, Profile profile) {
        super(uuid);
        this.credential = credential;
        this.profile = profile;
    }
}