package works.hop.jdbc.s_7_select_refactoring;

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
}
