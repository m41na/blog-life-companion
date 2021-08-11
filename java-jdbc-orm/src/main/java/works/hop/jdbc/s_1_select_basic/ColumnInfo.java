package works.hop.jdbc.s_1_select_basic;

public class ColumnInfo {

    String columnName;
    String attributeName;
    Class<?> attributeType;

    public ColumnInfo(String columnName, String attributeName, Class<?> attributeType) {
        this.columnName = columnName;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
    }
}
