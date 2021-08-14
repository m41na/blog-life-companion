package works.hop.jdbc.s_6_select_join_different_tables;

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

        //Address Entity
        registry.put(Address.class, new EntityMetadata(Address.class, null, List.of(
                ColumnInfo.builder().columnName("addr_city").attributeName("city").build(),
                ColumnInfo.builder().columnName("addr_state").attributeName("state").build(),
                ColumnInfo.builder().columnName("addr_postal_code").attributeName("zipCode").build()
        ), EntityType.EMBEDDABLE) {

            @Override
            public Address entityInstance() {
                return new Address();
            }
        });

        //User Entity
        registry.put(User.class, new EntityMetadata(User.class, "tbl_user", List.of(
                ColumnInfo.builder().columnName("id").attributeName("id").attributeType(UUID.class).isPkColumn(true).build(),
                ColumnInfo.builder().columnName("email_address").attributeName("emailAddress").build(),
                ColumnInfo.builder().columnName("nickname").attributeName("nickName").build(),
                ColumnInfo.builder().columnName("access_level").attributeName("accessLevel").attributeType(AccessLevel.class).isEnum(true).build(),
                ColumnInfo.builder().attributeName("address").attributeType(Address.class).isEmbedded(true).build(),
                ColumnInfo.builder().columnName("date_joined").attributeName("dateJoined").attributeType(LocalDate.class).build(),
                ColumnInfo.builder().columnName("task_assignee").attributeName("assignments").attributeType(Task.class).isCollection(true)
                        .inverseFkColumn("id").build()
        )) {

            @Override
            public User entityInstance() {
                return new User();
            }
        });

        //UserId Entity
        registry.put(UserId.class, new EntityMetadata(UserId.class, null, List.of(
                ColumnInfo.builder().columnName("email_address").attributeName("emailAddress").build(),
                ColumnInfo.builder().columnName("username").attributeName("userName").build()
        ), EntityType.COMPOSITE_PK) {

            @Override
            public UserId entityInstance() {
                return new UserId();
            }
        });

        //UserV2 Entity
        registry.put(UserV2.class, new EntityMetadata(UserV2.class, "tbl_user_v2", List.of(
                ColumnInfo.builder().attributeName("userId").attributeType(UserId.class).isCompositePk(true).build(),
                ColumnInfo.builder().columnName("nickname").attributeName("nickName").build(),
                ColumnInfo.builder().columnName("access_level").attributeName("accessLevel").attributeType(AccessLevel.class).isEnum(true).build(),
                ColumnInfo.builder().attributeName("address").attributeType(Address.class).isEmbedded(true).build(),
                ColumnInfo.builder().columnName("date_joined").attributeName("dateJoined").attributeType(LocalDate.class).build(),
                ColumnInfo.builder().attributeName("assignments").attributeType(TaskV2.class).isCollection(true)
                        .compositeColumns(new String[][]{{"task_assignee_email", "email_address"}, {"task_assignee_uname", "username"}})
                        .isInverseComposite(true).build()
        )) {

            @Override
            public UserV2 entityInstance() {
                return new UserV2();
            }
        });
    }
}
