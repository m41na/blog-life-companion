package works.hop.jdbc.s_4_select_1_to_1;

public class ColumnInfo {

    String columnName;
    String attributeName;
    Class<?> attributeType;
    Boolean isEmbedded;
    Boolean isEnum;
    Boolean isCompositePk;
    Boolean isFkColumn;

    public ColumnInfo(String columnName, String attributeName, Class<?> attributeType, Boolean isEmbedded, Boolean isEnum,
                      Boolean isCompositePk, Boolean isFkColumn) {
        this.columnName = columnName;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.isEmbedded = isEmbedded;
        this.isEnum = isEnum;
        this.isCompositePk = isCompositePk;
        this.isFkColumn = isFkColumn;
    }

    public static ColumnInfoBuilder builder(){
        return new ColumnInfoBuilder();
    }
}
