package works.hop.jdbc.s_1_select_basic;

import works.hop.jdbc.s_0_select.EntityMappingException;

import java.util.Map;
import java.util.Optional;

public abstract class EntityMetadata {

    final String tableName;
    final Map<String, ColumnInfo> columns;

    public EntityMetadata(String tableName, Map<String, ColumnInfo> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public abstract <T extends Entity> T entityInstance();

    public String resolveAttributeName(String columnName) {
        if (columns.containsKey(columnName)) {
            return columns.get(columnName).attributeName;
        }
        throw new EntityMappingException(String.format("column %s has no associated mapping in the metadata", columnName));
    }

    public Class<?> resolveAttributeType(String attributeName) {
        Optional<ColumnInfo> optional = columns.values().stream().filter(info -> info.attributeName.equals(attributeName))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get().attributeType;
        }
        throw new EntityMappingException(String.format("attribute %s not found in the metadata", attributeName));
    }
}
