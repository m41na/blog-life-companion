package works.hop.jdbc.s_8_validate_metadata;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Logger;

public class UserV4 implements Entity {

    static Logger LOG = Logger.getLogger(UserV4.class.getName());

    UserId userId;
    String nickName;
    AccessLevel accessLevel;
    Address address;
    LocalDate dateJoined;
    Collection<TaskV4> assignments;

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(UserV4.class);
    }

    @Override
    public <T> void set(String attribute, T value) {
        switch (attribute) {
            case "userId":
                this.userId = (UserId) value;
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
                this.assignments = (Collection<TaskV4>) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }
}
