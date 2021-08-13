package works.hop.jdbc.s_3_select_composite_pk;

import java.util.logging.Logger;

public class UserId implements Entity {

    static Logger LOG = Logger.getLogger(UserId.class.getName());

    String emailAddress;
    String userName;

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
}
