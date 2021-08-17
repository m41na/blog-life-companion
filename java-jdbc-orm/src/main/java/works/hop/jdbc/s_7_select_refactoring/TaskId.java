package works.hop.jdbc.s_7_select_refactoring;

import java.util.Objects;
import java.util.logging.Logger;

public class TaskId implements Entity {

    static Logger LOG = Logger.getLogger(TaskId.class.getName());

    Integer number;
    String name;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskId taskId = (TaskId) o;
        return number.equals(taskId.number) && name.equals(taskId.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name);
    }
}
