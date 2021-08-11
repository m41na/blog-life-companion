package works.hop.jdbc.s_2_select_embedded;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Customer implements Entity {

    static Logger LOG = Logger.getLogger(Customer.class.getName());

    UUID id;
    String firstName;
    String lastName;
    Level level;
    Address address;
    LocalDate dateJoined;

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(Customer.class);
    }

    @Override
    public <T> void set(String attribute, T value) {
        switch (attribute) {
            case "id":
                this.id = (UUID) value;
                break;
            case "firstName":
                this.firstName = (String) value;
                break;
            case "lastName":
                this.lastName = (String) value;
                break;
            case "level":
                this.level = Level.valueOf((String)value);
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
