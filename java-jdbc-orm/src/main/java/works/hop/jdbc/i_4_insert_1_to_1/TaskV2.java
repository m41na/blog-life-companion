package works.hop.jdbc.i_4_insert_1_to_1;

import works.hop.jdbc.i_0_insert.EntityMappingException;

import java.time.LocalDate;
import java.util.logging.Logger;

public class TaskV2 implements Entity {

    static Logger LOG = Logger.getLogger(TaskV2.class.getName());

    TaskId taskId;
    Boolean completed;
    LocalDate dateCreated;
    TaskV2 parentTask;

    public TaskV2() {
    }

    public TaskV2(TaskId taskId, Boolean completed, LocalDate dateCreated, TaskV2 parentTask) {
        this.taskId = taskId;
        this.completed = completed;
        this.dateCreated = dateCreated;
        this.parentTask = parentTask;
    }

    @Override
    public EntityMetadata metadata() {
        return EntityRegistry.registry.get(TaskV2.class);
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
                this.parentTask = (TaskV2) value;
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
