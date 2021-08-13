package works.hop.jdbc.s_5_select_many_to_1;

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
    String[][] compositeColumns;

    public ColumnInfo(String columnName, String attributeName, Class<?> attributeType, Boolean isEmbedded, Boolean isEnum,
                      Boolean isCompositePk, Boolean isPkColumn, Boolean isFkColumn, Boolean isCollection, String[][] compositeColumns) {
        this.columnName = columnName;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.isEmbedded = isEmbedded;
        this.isEnum = isEnum;
        this.isCompositePk = isCompositePk;
        this.isPkColumn = isPkColumn;
        this.isFkColumn = isFkColumn;
        this.isCollection = isCollection;
        this.compositeColumns = compositeColumns;
    }

    public static ColumnInfoBuilder builder() {
        return new ColumnInfoBuilder();
    }
}
