package Field;

import java.util.Objects;
import java.util.UUID;

public class User extends Storable {
    public final Credential credential;
    public final Profile profile;

    public User(Credential credential, Profile profile) {
        super();
        this.credential = credential;
        this.profile = profile;
    }

    public User(UUID uuid, Credential credential, Profile profile) {
        super(uuid);
        this.credential = credential;
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return credential.equals(user.credential) && profile.equals(user.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credential, profile);
    }
}