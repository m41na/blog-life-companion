package works.hop.jdbc.s_2_select_embedded;

public class ColumnInfo {

    String columnName;
    String attributeName;
    Class<?> attributeType;
    Boolean isEmbedded;
    Boolean isEnum;

    public ColumnInfo(String columnName, String attributeName, Class<?> attributeType, Boolean isEmbedded, Boolean isEnum) {
        this.columnName = columnName;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
        this.isEmbedded = isEmbedded;
        this.isEnum = isEnum;
    }

    public static ColumnInfoBuilder builder(){
        return new ColumnInfoBuilder();
    }
}
