package works.hop.jdbc.i_6_insert_join_different_tables;

public class ColumnInfo {

    String columnName;
    String attributeName;
    Class<?> attributeType;
    Boolean isAutoGenerated;
    Boolean isEmbedded;
    Boolean isEnum;
    Boolean isEnumOrdinal;
    Boolean isCompositePk;
    Boolean isPkColumn;
    Boolean isFkColumn;
    Boolean isCollection;
    String inverseFkColumn;
    String[][] compositeColumns;
    Boolean isInverseComposite;
    JoinTable joinTable;

    public ColumnInfo(String columnName, String attributeName, Class<?> attributeType, Boolean isAutoGenerated, Boolean isEmbedded, Boolean isEnum,
                      Boolean isEnumOrdinal, Boolean isCompositePk, Boolean isPkColumn, Boolean isFkColumn, Boolean isCollection, String inverseFkColumn,
                      String[][] compositeColumns, Boolean isInverseComposite, JoinTable joinTable) {
        this.columnName = columnName;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.isAutoGenerated = isAutoGenerated;
        this.isEmbedded = isEmbedded;
        this.isEnum = isEnum;
        this.isEnumOrdinal = isEnumOrdinal;
        this.isCompositePk = isCompositePk;
        this.isPkColumn = isPkColumn;
        this.isFkColumn = isFkColumn;
        this.isCollection = isCollection;
        this.inverseFkColumn = inverseFkColumn;
        this.compositeColumns = compositeColumns;
        this.isInverseComposite = isInverseComposite;
        this.joinTable = joinTable;
    }

    public static ColumnInfoBuilder builder() {
        return new ColumnInfoBuilder();
    }
}
