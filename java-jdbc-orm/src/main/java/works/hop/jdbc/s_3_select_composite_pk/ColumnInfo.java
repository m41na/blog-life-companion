package works.hop.jdbc.s_3_select_composite_pk;

public class ColumnInfo {

    String columnName;
    String attributeName;
    Class<?> attributeType;
    Boolean isEmbedded;
    Boolean isEnum;
    Boolean isCompositePk;

    public ColumnInfo(String columnName, String attributeName, Class<?> attributeType, Boolean isEmbedded, Boolean isEnum, Boolean isCompositePk) {
        this.columnName = columnName;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.isEmbedded = isEmbedded;
        this.isEnum = isEnum;
        this.isCompositePk = isCompositePk;
    }

    public static ColumnInfoBuilder builder() {
        return new ColumnInfoBuilder();
    }
}
