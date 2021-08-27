package works.hop.jdbc.i_2_insert_embedded;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.util.logging.Logger;

public class Address implements Entity {

    static Logger LOG = Logger.getLogger(Address.class.getName());

    String city;
    String state;
    String zipCode;

    public Address() {
    }

    public Address(String city, String state, String zipCode) {
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(Address.class);
    }

    @Override
    public <T> void set(String attribute, T value) {
        switch (attribute) {
            case "city":
                this.city = (String) value;
                break;
            case "state":
                this.state = (String) value;
                break;
            case "zipCode":
                this.zipCode = (String) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }

    @Override
    public Object get(String attribute) {
        switch (attribute) {
            case "city":
                return this.city;
            case "state":
                return this.state;
            case "zipCode":
                return this.zipCode;
            default:
                LOG.warning("what the heck are you doing?");
                throw new EntityMappingException(String.format("There is no attribute in the entity with the name %s", attribute));
        }
    }
}
