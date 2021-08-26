package works.hop.jdbc.i_1_insert_basic;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class Task implements Entity {

    static Logger LOG = Logger.getLogger(Task.class.getName());
    static EntityMetadata metadata = new EntityMetadata("tbl_task", Set.of(
            new ColumnInfo("id", "id", UUID.class, true),
            new ColumnInfo("name", "name", String.class, false),
            new ColumnInfo("done", "completed", Boolean.class, false),
            new ColumnInfo("task_created", "dateCreated", LocalDate.class, false)
    )) {
        @Override
        public Task entityInstance() {
            return new Task();
        }
    };
    UUID id;
    String name;
    Boolean completed;
    LocalDate dateCreated;

    public Task() {
    }

    public Task(String name, Boolean completed) {
        this.name = name;
        this.completed = completed;
    }

    @Override
    public EntityMetadata metadata() {
        return Task.metadata;
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
            default:
                LOG.warning("what the heck are you doing?");
                throw new EntityMappingException(String.format("There is no attribute in the entity with the name %s", attribute));
        }
    }
}
