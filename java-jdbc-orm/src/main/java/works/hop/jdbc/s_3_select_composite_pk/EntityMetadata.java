package works.hop.jdbc.s_3_select_composite_pk;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class EntityMetadata {

    final String tableName;
    final EntityType entityType;
    final List<ColumnInfo> columns;

    public EntityMetadata(Class<? extends Entity> entityClass, String tableName, List<ColumnInfo> columns) {
        this(entityClass, tableName, columns, EntityType.IDENTIFIABLE);
    }

    public EntityMetadata(Class<? extends Entity> entityClass, String tableName, List<ColumnInfo> columns, EntityType entityType) {
        this.tableName = tableName;
        this.entityType = entityType;
        this.columns = columns;
        EntityRegistry.registry.put(entityClass, this);
    }

    public abstract <T extends Entity> T entityInstance();

    public Optional<ColumnInfo> resolveByColumnName(String columnName){
        return columns.stream()
                .filter(info -> info.columnName != null)
                .filter(info -> info.columnName.equals(columnName))
                .findFirst();
    }

    public boolean containsEmbedded(){
        return columns.stream().anyMatch(info -> info.isEmbedded);
    }

    public List<ColumnInfo> embeddedColumns(){
        return columns.stream().filter(info -> info.isEmbedded).collect(Collectors.toList());
    }

    public boolean containsCompositePk(){
        return columns.stream().anyMatch(info -> info.isCompositePk);
    }

    public ColumnInfo compositePkColumn(){
        return columns.stream().filter(info -> info.isCompositePk).findFirst().orElseThrow();
    }
}
