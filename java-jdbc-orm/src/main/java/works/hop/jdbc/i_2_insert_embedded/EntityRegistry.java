package works.hop.jdbc.i_2_insert_embedded;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class EntityRegistry {

    public static final Map<Class<? extends Entity>, EntityMetadata> registry = new HashMap<>();

    static {
        //Address Entity
        registry.put(Address.class, new EntityMetadata(Address.class, null, Set.of(
                ColumnInfo.builder().columnName("addr_city").attributeName("city").build(),
                ColumnInfo.builder().columnName("addr_state").attributeName("state").build(),
                ColumnInfo.builder().columnName("addr_postal_code").attributeName("zipCode").build()
        ), EntityType.EMBEDDABLE) {

            @Override
            public Address entityInstance() {
                return new Address();
            }
        });

        //Customer Entity
        registry.put(Customer.class, new EntityMetadata(Customer.class, "tbl_customer", Set.of(
                ColumnInfo.builder().columnName("member_id").attributeName("id").isAutoGenerated(true).attributeType(UUID.class).build(),
                ColumnInfo.builder().columnName("first_name").attributeName("firstName").build(),
                ColumnInfo.builder().columnName("last_name").attributeName("lastName").build(),
                ColumnInfo.builder().columnName("member_level").attributeName("level").attributeType(Level.class).isEnum(true).build(),
                ColumnInfo.builder().attributeName("address").attributeType(Address.class).isEmbedded(true).build(),
                ColumnInfo.builder().columnName("date_joined").attributeName("dateJoined").attributeType(LocalDate.class).build()
        )) {

            @Override
            public Customer entityInstance() {
                return new Customer();
            }
        });
    }
}
