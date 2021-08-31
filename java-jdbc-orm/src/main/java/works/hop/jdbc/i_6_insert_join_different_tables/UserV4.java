package works.hop.jdbc.i_6_insert_join_different_tables;

import works.hop.jdbc.i_0_insert.EntityMappingException;

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

    @Override
    public Object get(String attribute) {
        switch (attribute) {
            case "userId":
                return this.userId ;
            case "nickName":
                return this.nickName;
            case "accessLevel":
                return this.accessLevel;
            case "address":
                return this.address;
            case "dateJoined":
                return this.dateJoined;
            case "assignments":
                return this.assignments;
            default:
                LOG.warning("what the heck are you doing?");
                throw new EntityMappingException(String.format("There is no attribute in the entity with the name %s", attribute));
        }
    }
}
