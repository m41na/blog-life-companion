package works.hop.jdbc.s_6_select_join_different_tables;

import java.util.Objects;
import java.util.logging.Logger;

public class UserId implements Entity, Comparable<UserId> {

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

    @Override
    public int compareTo(UserId o) {
        if (this == o) return 0;
        int comparison = this.emailAddress.compareTo(o.emailAddress);
        if (comparison != 0) return comparison;
        return this.userName.compareTo(o.userName);
    }
}
