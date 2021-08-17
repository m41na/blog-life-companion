package works.hop.jdbc.s_7_select_refactoring;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRegistry {

    public static final Map<Class<? extends Entity>, EntityMetadata> registry = new HashMap<>();

    static {
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

        //TaskV4 Entity
        registry.put(TaskV4.class, new EntityMetadata(TaskV4.class, "tbl_task_v4", List.of(
                ColumnInfo.builder().attributeName("taskId").attributeType(TaskId.class).isCompositePk(true).build(),
                ColumnInfo.builder().columnName("done").attributeName("completed").attributeType(Boolean.class).build(),
                ColumnInfo.builder().columnName("task_created").attributeName("dateCreated").attributeType(LocalDate.class).build(),
                ColumnInfo.builder().attributeName("parentTask").attributeType(TaskV4.class).isFKColumn(true)
                        .compositeColumns(new String[][]{{"parent_task_num", "num"}, {"parent_task_name", "name"}}).build(),
                ColumnInfo.builder().attributeName("dependsOn").attributeType(TaskV4.class).isCollection(true)
                        .compositeColumns(new String[][]{{"parent_task_num", "num"}, {"parent_task_name", "name"}}).build()
        )) {

            @Override
            public TaskV4 entityInstance() {
                return new TaskV4();
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

        //UserV4 Entity
        registry.put(UserV4.class, new EntityMetadata(UserV4.class, "tbl_user_v4", List.of(
                ColumnInfo.builder().attributeName("userId").attributeType(UserId.class).isCompositePk(true).build(),
                ColumnInfo.builder().columnName("nickname").attributeName("nickName").build(),
                ColumnInfo.builder().columnName("access_level").attributeName("accessLevel").attributeType(AccessLevel.class).isEnum(true).build(),
                ColumnInfo.builder().attributeName("address").attributeType(Address.class).isEmbedded(true).build(),
                ColumnInfo.builder().columnName("date_joined").attributeName("dateJoined").attributeType(LocalDate.class).build(),
                ColumnInfo.builder().attributeName("assignments").attributeType(TaskV4.class).isCollection(true)
                        .joinTable(JoinTable.builder().tableName("tbl_user_task_v4").compositeColumns("email_address", "username").inverseColumns("num", "name")
                                .onColumns("task_num", "task_name").whereColumns("task_assignee_email", "task_assignee_uname")
                                .build()).build()
        )) {

            @Override
            public UserV4 entityInstance() {
                return new UserV4();
            }
        });
    }
}
