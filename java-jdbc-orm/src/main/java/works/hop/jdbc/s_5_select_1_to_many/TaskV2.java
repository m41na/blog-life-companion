package works.hop.jdbc.s_5_select_1_to_many;

import java.time.LocalDate;
import java.util.logging.Logger;

public class TaskV2 implements Entity {

    static Logger LOG = Logger.getLogger(TaskV2.class.getName());

    TaskId taskId;
    Boolean completed;
    LocalDate dateCreated;
    TaskV2 parentTask;

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
}
