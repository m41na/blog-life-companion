package works.hop.jdbc.i_2_insert_embedded;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.time.LocalDate;
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

    public Customer() {
    }

    public Customer(UUID id, String firstName, String lastName, Level level, Address address, LocalDate dateJoined) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.address = address;
        this.dateJoined = dateJoined;
    }

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
                this.level = Level.valueOf((String) value);
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

    @Override
    public Object get(String attribute) {
        switch (attribute) {
            case "id":
                return this.id;
            case "firstName":
                return this.firstName;
            case "lastName":
                return this.lastName;
            case "level":
                return this.level;
            case "address":
                return this.address;
            case "dateJoined":
                return this.dateJoined;
            default:
                LOG.warning("what the heck are you doing?");
                throw new EntityMappingException(String.format("There is no attribute in the entity with the name %s", attribute));
        }
    }
}
