package works.hop.jdbc.s_3_select_composite_pk;

import java.time.LocalDate;
import java.util.logging.Logger;

public class User implements Entity {

    static Logger LOG = Logger.getLogger(User.class.getName());

    UserId userId;
    String nickName;
    AccessLevel accessLevel;
    Address address;
    LocalDate dateJoined;

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(User.class);
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
                this.accessLevel = AccessLevel.valueOf((String)value);
                break;
            case "address":
                this.address = (Address) value;
                break;
            case "dateJoined":
                this.dateJoined = (LocalDate) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }
}
