package works.hop.jdbc.s_4_select_1_to_1;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EntityRegistry {

    public static final Map<Class<? extends Entity>, EntityMetadata> registry = new HashMap<>();

    static {
        //Task Entity
        registry.put(Task.class, new EntityMetadata(Task.class, "tbl_task", List.of(
                ColumnInfo.builder().columnName("id").attributeName("id").attributeType(UUID.class).isPkColumn(true).build(),
                ColumnInfo.builder().columnName("name").attributeName("name").build(),
                ColumnInfo.builder().columnName("done").attributeName("completed").attributeType(Boolean.class).build(),
                ColumnInfo.builder().columnName("task_created").attributeName("dateCreated").attributeType(LocalDate.class).build(),
                ColumnInfo.builder().columnName("parent_task").attributeName("parentTask").attributeType(Task.class).isFKColumn(true).build()
        )) {

            @Override
            public Task entityInstance() {
                return new Task();
            }
        });
    }
}
