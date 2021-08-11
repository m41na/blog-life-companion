package works.hop.jdbc.s_2_select_embedded;

import java.util.logging.Logger;

public class Address implements Entity {

    static Logger LOG = Logger.getLogger(Address.class.getName());

    String city;
    String state;
    String zipCode;

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
}
