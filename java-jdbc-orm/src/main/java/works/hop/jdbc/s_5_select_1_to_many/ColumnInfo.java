package works.hop.jdbc.s_5_select_1_to_many;

public class ColumnInfo {

    String columnName;
    String attributeName;
    Class<?> attributeType;
    Boolean isEmbedded;
    Boolean isEnum;
    Boolean isCompositePk;
    Boolean isPkColumn;
    Boolean isFkColumn;
    String[][] compositeColumns;

    public ColumnInfo(String columnName, String attributeName, Class<?> attributeType, Boolean isEmbedded,
                      Boolean isEnum, Boolean isCompositePk, Boolean isPkColumn, Boolean isFkColumn, String[][] compositeColumns) {
        this.columnName = columnName;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.isEmbedded = isEmbedded;
        this.isEnum = isEnum;
        this.isCompositePk = isCompositePk;
        this.isPkColumn = isPkColumn;
        this.isFkColumn = isFkColumn;
        this.compositeColumns = compositeColumns;
    }

    public static ColumnInfoBuilder builder() {
        return new ColumnInfoBuilder();
    }
}
