package works.hop.jdbc.i_4_insert_1_to_1;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Logger;

public class Task implements Entity {

    static Logger LOG = Logger.getLogger(Task.class.getName());

    UUID id;
    String name;
    Boolean completed;
    LocalDate dateCreated;
    Task parentTask;

    public Task() {
    }

    public Task(UUID id, String name, Boolean completed, LocalDate dateCreated, Task parentTask) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.dateCreated = dateCreated;
        this.parentTask = parentTask;
    }

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(Task.class);
    }

    @Override
    public <T> void set(String attribute, T value) {
        switch (attribute) {
            case "id":
                this.id = (UUID) value;
                break;
            case "name":
                this.name = (String) value;
                break;
            case "completed":
                this.completed = (Boolean) value;
                break;
            case "dateCreated":
                this.dateCreated = (LocalDate) value;
                break;
            case "parentTask":
                this.parentTask = (Task) value;
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
            case "name":
                return this.name;
            case "completed":
                return this.completed;
            case "dateCreated":
                return this.dateCreated;
            case "parentTask":
                return this.parentTask;
            default:
                LOG.warning("what the heck are you doing?");
                throw new EntityMappingException(String.format("There is no attribute in the entity with the name %s", attribute));
        }
    }
}
