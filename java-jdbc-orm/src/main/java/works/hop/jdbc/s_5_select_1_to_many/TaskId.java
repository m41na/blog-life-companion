package works.hop.jdbc.s_5_select_1_to_many;

import java.util.logging.Logger;

public class TaskId implements Entity, Comparable<TaskId> {

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
    public int compareTo(TaskId o) {
        if (this == o) return 0;
        int comparison = this.name.compareTo(o.name);
        if (comparison != 0) return comparison;
        return this.number.compareTo(o.number);
    }
}
