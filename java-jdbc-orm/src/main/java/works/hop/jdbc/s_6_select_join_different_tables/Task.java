package works.hop.jdbc.s_6_select_join_different_tables;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;

public class Task implements Entity {

    static Logger LOG = Logger.getLogger(Task.class.getName());

    UUID id;
    String name;
    Boolean completed;
    LocalDate dateCreated;
    Task parentTask;
    Collection<Task> dependsOn;

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
            case "dependsOn":
                this.dependsOn = (Collection<Task>) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }
}
