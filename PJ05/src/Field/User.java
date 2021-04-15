package Field;

import java.util.UUID;

public class User extends Storable
{
    private final UUID uuid;
    private final Credential credential;
    private final Profile profile;

    public User(Credential credential, Profile profile)
    {
        this.uuid = UUID.fromString(credential.usrName);
        this.credential = credential;
        this.profile = profile;
    }

    public User(UUID uuid, Credential credential, Profile profile)
    {
        this.uuid = uuid;
        this.credential = credential;
        this.profile = profile;
    }

    public static User parse(String raw)
    {
        //TODO expected that user == Field.User.parse(user.toString())
        return null;
    }

    public User updateCredential(Credential credential)
    {
        return new User(
                this.uuid,
                credential,
                this.profile
        );
    }

    public User updateProfile(Profile profile)
    {
        return new User(
                this.uuid,
                this.credential,
                profile
        );

    }

    public UUID getUuid()
    {
        return uuid;
    }

    public Credential getCredential()
    {
        return credential;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public String toString()
    {
        //TODO
        return null;
    }
}

