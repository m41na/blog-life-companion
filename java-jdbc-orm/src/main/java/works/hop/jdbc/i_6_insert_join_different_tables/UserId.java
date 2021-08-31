package works.hop.jdbc.i_6_insert_join_different_tables;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.util.Objects;
import java.util.logging.Logger;

public class UserId implements Entity {

    static Logger LOG = Logger.getLogger(UserId.class.getName());

    String emailAddress;
    String userName;

    public UserId() {
    }

    public UserId(String emailAddress, String userName) {
        this.emailAddress = emailAddress;
        this.userName = userName;
    }

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(UserId.class);
    }

    @Override
    public <T> void set(String attribute, T value) {
        switch (attribute) {
            case "emailAddress":
                this.emailAddress = (String) value;
                break;
            case "userName":
                this.userName = (String) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }

    @Override
    public Object get(String attribute) {
        switch (attribute) {
            case "emailAddress":
                return this.emailAddress;
            case "userName":
                return this.userName ;
            default:
                LOG.warning("what the heck are you doing?");
                throw new EntityMappingException(String.format("There is no attribute in the entity with the name %s", attribute));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return emailAddress.equals(userId.emailAddress) && userName.equals(userId.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, userName);
    }
}
