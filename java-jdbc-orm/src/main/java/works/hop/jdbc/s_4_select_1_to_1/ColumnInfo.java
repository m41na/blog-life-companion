package works.hop.jdbc.s_4_select_1_to_1;

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
