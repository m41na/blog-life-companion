package works.hop.jdbc.s_5_select_many_to_1;

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
                ColumnInfo.builder().columnName("parent_task").attributeName("parentTask").attributeType(Task.class).isFKColumn(true).build(),
                ColumnInfo.builder().columnName("parent_task").attributeName("dependsOn").attributeType(Task.class).isCollection(true).build()
        )) {

            @Override
            public Task entityInstance() {
                return new Task();
            }
        });

        //TaskId Entity
        registry.put(TaskId.class, new EntityMetadata(TaskId.class, null, List.of(
                ColumnInfo.builder().columnName("num").attributeName("number").attributeType(Integer.class).build(),
                ColumnInfo.builder().columnName("name").attributeName("name").build()
        ), EntityType.COMPOSITE_PK) {

            @Override
            public TaskId entityInstance() {
                return new TaskId();
            }
        });

        //TaskV2 Entity
        registry.put(TaskV2.class, new EntityMetadata(TaskV2.class, "tbl_task_v2", List.of(
                ColumnInfo.builder().attributeName("taskId").attributeType(TaskId.class).isCompositePk(true).build(),
                ColumnInfo.builder().columnName("done").attributeName("completed").attributeType(Boolean.class).build(),
                ColumnInfo.builder().columnName("task_created").attributeName("dateCreated").attributeType(LocalDate.class).build(),
                ColumnInfo.builder().attributeName("parentTask").attributeType(TaskV2.class).isFKColumn(true)
                        .compositeColumns(new String[][]{{"parent_task_num", "num"}, {"parent_task_name", "name"}}).build(),
                ColumnInfo.builder().attributeName("dependsOn").attributeType(TaskV2.class).isCollection(true)
                        .compositeColumns(new String[][]{{"parent_task_num", "num"}, {"parent_task_name", "name"}}).build()
        )) {

            @Override
            public TaskV2 entityInstance() {
                return new TaskV2();
            }
        });
    }
}
