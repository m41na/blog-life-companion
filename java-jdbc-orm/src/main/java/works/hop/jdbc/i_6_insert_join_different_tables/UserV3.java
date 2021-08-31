package works.hop.jdbc.i_6_insert_join_different_tables;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Logger;

public class UserV3 implements Entity {

    static Logger LOG = Logger.getLogger(UserV3.class.getName());

    UUID id;
    String emailAddress;
    String nickName;
    AccessLevel accessLevel;
    Address address;
    LocalDate dateJoined;
    Collection<TaskV3> assignments;

    public UserV3() {
    }

    public UserV3(UUID id, String emailAddress, String nickName, AccessLevel accessLevel, Address address, Collection<TaskV3> assignments) {
        this(id, emailAddress, nickName, accessLevel, address, LocalDate.now(), Collections.emptyList());
    }

    public UserV3(UUID id, String emailAddress, String nickName, AccessLevel accessLevel, Address address, LocalDate dateJoined, Collection<TaskV3> assignments) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.nickName = nickName;
        this.accessLevel = accessLevel;
        this.address = address;
        this.dateJoined = dateJoined;
        this.assignments = assignments;
    }

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(UserV3.class);
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
                this.assignments = (Collection<TaskV3>) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }

    @Override
    public Object get(String attribute) {
        switch (attribute) {
            case "id":
                return this.id;
            case "emailAddress":
                return this.emailAddress;
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
