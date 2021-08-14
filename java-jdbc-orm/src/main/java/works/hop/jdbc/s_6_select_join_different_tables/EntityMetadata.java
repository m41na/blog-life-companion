package works.hop.jdbc.s_6_select_join_different_tables;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Optional<ColumnInfo> resolveByColumnName(String columnName) {
        return columns.stream()
                .filter(info -> info.columnName != null)
                .filter(info -> info.columnName.equals(columnName))
                .findFirst();
    }

    public ColumnInfo pkColumn() {
        return columns.stream().filter(info -> info.isPkColumn).findFirst().orElseThrow();
    }

    public Boolean containsPkColumn() {
        return columns.stream().anyMatch(info -> info.isPkColumn != null && info.isPkColumn);
    }

    public Boolean containsEmbedded() {
        return columns.stream().anyMatch(info -> info.isEmbedded != null && info.isEmbedded);
    }

    public List<ColumnInfo> embeddedColumns() {
        return columns.stream().filter(info -> info.isEmbedded).collect(Collectors.toList());
    }

    public Boolean containsCompositePk() {
        return columns.stream().anyMatch(info -> info.isCompositePk != null && info.isCompositePk);
    }

    public ColumnInfo compositePkColumn() {
        return columns.stream().filter(info -> info.isCompositePk).findFirst().orElseThrow();
    }

    public Boolean containsFkColumns() {
        return columns.stream().anyMatch(info -> info.isFkColumn != null && info.isFkColumn);
    }

    public List<ColumnInfo> fkColumns() {
        return columns.stream().filter(info -> info.isFkColumn).collect(Collectors.toList());
    }

    public Boolean containsCollectionColumns() {
        return columns.stream().anyMatch(info -> info.isCollection != null && info.isCollection);
    }

    public List<ColumnInfo> collectionColumns() {
        return columns.stream().filter(info -> info.isCollection).collect(Collectors.toList());
    }

    public String createJoinQuery(String fkColumnTable, String fkColumnName) {
        ColumnInfo pkColumnInfo = pkColumn();
        String[] query = new String[]{
                "select PK_TABLE.* from " + tableName + " PK_TABLE",
                "inner join " + fkColumnTable + " FK_TABLE on FK_TABLE." + fkColumnName + " = PK_TABLE." + pkColumnInfo.columnName,
                "where FK_TABLE." + fkColumnName + " = ?"
        };
        return String.join(" ", query);
    }

    public String createInverseJoinQuery(String fkColumnTable, String fkColumnName) {
        ColumnInfo pkColumnInfo = pkColumn();
        String[] query = new String[]{
                "select PK_TABLE.* from " + tableName + " PK_TABLE",
                "inner join " + fkColumnTable + " FK_TABLE on FK_TABLE." + pkColumnInfo.columnName + " = PK_TABLE." + fkColumnName,
                "where PK_TABLE." + fkColumnName + " = ?"
        };
        return String.join(" ", query);
    }

    public String createCompositeJoinQuery(String fkColumnTable, String[][] compositeColumns) {
        String joinOn = Stream.of(compositeColumns).map(fkColumnNames -> "" +
                "FK_TABLE." + fkColumnNames[0] + " = PK_TABLE." + fkColumnNames[1]).collect(Collectors.joining(" and "));
        String joinWhere = Stream.of(compositeColumns).map(fkColumnNames -> "" +
                "FK_TABLE." + fkColumnNames[0] + " = ?").collect(Collectors.joining(" and "));
        String[] query = new String[]{
                "select PK_TABLE.* from " + tableName + " PK_TABLE",
                "inner join " + fkColumnTable + " FK_TABLE on " + joinOn,
                "where " + joinWhere
        };

        return String.join(" ", query);
    }

    public String createInverseCompositeJoinQuery(String fkColumnTable, String[][] compositeColumns) {
        String joinOn = Stream.of(compositeColumns).map(fkColumnNames -> "" +
                "PK_TABLE." + fkColumnNames[0] + " = FK_TABLE." + fkColumnNames[1]).collect(Collectors.joining(" and "));
        String joinWhere = Stream.of(compositeColumns).map(fkColumnNames -> "" +
                "PK_TABLE." + fkColumnNames[0] + " = ?").collect(Collectors.joining(" and "));
        String[] query = new String[]{
                "select PK_TABLE.* from " + tableName + " PK_TABLE",
                "inner join " + fkColumnTable + " FK_TABLE on " + joinOn,
                "where " + joinWhere
        };

        return String.join(" ", query);
    }
}
