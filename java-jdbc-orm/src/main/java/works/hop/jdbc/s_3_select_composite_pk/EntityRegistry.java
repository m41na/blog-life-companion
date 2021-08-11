package works.hop.jdbc.s_3_select_composite_pk;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EntityRegistry {

    public static final Map<Class<? extends Entity>, EntityMetadata> registry = new HashMap<>();

    static {
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

        //User Entity
        registry.put(User.class, new EntityMetadata(User.class, "tbl_user", List.of(
                ColumnInfo.builder().attributeName("userId").attributeType(UserId.class).isCompositePk(true).build(),
                ColumnInfo.builder().columnName("nickname").attributeName("nickName").build(),
                ColumnInfo.builder().columnName("access_level").attributeName("accessLevel").attributeType(AccessLevel.class).isEnum(true).build(),
                ColumnInfo.builder().attributeName("address").attributeType(Address.class).isEmbedded(true).build(),
                ColumnInfo.builder().columnName("date_joined").attributeName("dateJoined").attributeType(LocalDate.class).build()
        )) {

            @Override
            public User entityInstance() {
                return new User();
            }
        });
    }
}
