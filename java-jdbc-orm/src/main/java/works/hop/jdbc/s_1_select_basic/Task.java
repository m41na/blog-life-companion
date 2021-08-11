package works.hop.jdbc.s_1_select_basic;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class Task implements Entity {

    static Logger LOG = Logger.getLogger(Task.class.getName());
    static EntityMetadata metadata = new EntityMetadata("tbl_task", Map.of(
            "id", new ColumnInfo("id", "id", UUID.class),
            "name", new ColumnInfo("name", "name", String.class),
            "done", new ColumnInfo("done", "completed", Boolean.class),
            "task_created", new ColumnInfo("task_created", "dateCreated", LocalDate.class)
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
}
