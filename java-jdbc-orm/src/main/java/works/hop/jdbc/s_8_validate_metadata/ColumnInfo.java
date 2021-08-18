package works.hop.jdbc.s_8_validate_metadata;

public class ColumnInfo {

    String columnName;
    String attributeName;
    Class<?> attributeType;
    Boolean isEmbedded;
    Boolean isEnum;
    Boolean isCompositePk;
    Boolean isPkColumn;
    Boolean isFkColumn;
    Boolean isCollection;
    String inverseFkColumn;
    String[][] compositeColumns;
    Boolean isInverseComposite;
    JoinTable joinTable;
    String[] limitColumns;

    public ColumnInfo(String columnName, String attributeName, Class<?> attributeType, Boolean isEmbedded, Boolean isEnum,
                      Boolean isCompositePk, Boolean isPkColumn, Boolean isFkColumn, Boolean isCollection, String inverseFkColumn,
                      String[][] compositeColumns, Boolean isInverseComposite, JoinTable joinTable, String[] limitColumns) {
        this.columnName = columnName;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.isEmbedded = isEmbedded;
        this.isEnum = isEnum;
        this.isCompositePk = isCompositePk;
        this.isPkColumn = isPkColumn;
        this.isFkColumn = isFkColumn;
        this.isCollection = isCollection;
        this.inverseFkColumn = inverseFkColumn;
        this.compositeColumns = compositeColumns;
        this.isInverseComposite = isInverseComposite;
        this.joinTable = joinTable;
        this.limitColumns = limitColumns;
    }

    public static ColumnInfoBuilder builder() {
        return new ColumnInfoBuilder();
    }
}
