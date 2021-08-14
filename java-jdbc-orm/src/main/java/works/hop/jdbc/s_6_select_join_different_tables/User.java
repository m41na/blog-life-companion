package works.hop.jdbc.s_6_select_join_different_tables;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;

public class User implements Entity {

    static Logger LOG = Logger.getLogger(User.class.getName());

    UUID id;
    String emailAddress;
    String nickName;
    AccessLevel accessLevel;
    Address address;
    LocalDate dateJoined;
    Collection<Task> assignments;

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(User.class);
    }

    @Override
    public <T> void set(String attribute, T value) {
        switch (attribute) {
            case "id":
                this.id = (UUID) value;
                break;
            case "emailAddress":
                this.emailAddress = (String) value;
                break;
            case "nickName":
                this.nickName = (String) value;
                break;
            case "accessLevel":
                this.accessLevel = AccessLevel.valueOf((String) value);
                break;
            case "address":
                this.address = (Address) value;
                break;
            case "dateJoined":
                this.dateJoined = (LocalDate) value;
                break;
            case "assignments":
                this.assignments = (Collection<Task>) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }
}
