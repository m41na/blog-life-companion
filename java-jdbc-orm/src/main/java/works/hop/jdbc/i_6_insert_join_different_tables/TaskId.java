package works.hop.jdbc.i_6_insert_join_different_tables;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.util.logging.Logger;

public class TaskId implements Entity {

    static Logger LOG = Logger.getLogger(TaskId.class.getName());

    Integer number;
    String name;

    public TaskId() {
    }

    public TaskId(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(TaskId.class);
    }

    @Override
    public <T> void set(String attribute, T value) {
        switch (attribute) {
            case "number":
                this.number = (Integer) value;
                break;
            case "name":
                this.name = (String) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }

    @Override
    public Object get(String attribute) {
        switch (attribute) {
            case "number":
                return this.number;
            case "name":
                return this.name;
            default:
                LOG.warning("what the heck are you doing?");
                throw new EntityMappingException(String.format("There is no attribute in the entity with the name %s", attribute));
        }
    }
}
