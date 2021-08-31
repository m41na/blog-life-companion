package works.hop.jdbc.i_6_insert_join_different_tables;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Logger;

public class TaskV4 implements Entity {

    static Logger LOG = Logger.getLogger(TaskV4.class.getName());

    TaskId taskId;
    Boolean completed;
    LocalDate dateCreated;
    TaskV4 parentTask;
    Collection<TaskV4> dependsOn;

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(TaskV4.class);
    }

    @Override
    public <T> void set(String attribute, T value) {
        switch (attribute) {
            case "taskId":
                this.taskId = (TaskId) value;
                break;
            case "completed":
                this.completed = (Boolean) value;
                break;
            case "dateCreated":
                this.dateCreated = (LocalDate) value;
                break;
            case "parentTask":
                this.parentTask = (TaskV4) value;
                break;
            case "dependsOn":
                this.dependsOn = (Collection<TaskV4>) value;
                break;
            default:
                LOG.warning("what the heck are you doing?");
        }
    }

    @Override
    public Object get(String attribute) {
        switch (attribute) {
            case "taskId":
                return this.taskId;
            case "completed":
               return  this.completed;
            case "dateCreated":
                return this.dateCreated;
            case "parentTask":
                return this.parentTask;
            case "dependsOn":
                return this.dependsOn;
            default:
                LOG.warning("what the heck are you doing?");
                throw new EntityMappingException(String.format("There is no attribute in the entity with the name %s", attribute));
        }
    }
}
