package works.hop.jdbc.s_4_select_1_to_1;

public class ColumnInfoBuilder {

    private String columnName;
    private String attributeName;
    private Class<?> attributeType = String.class;
    private Boolean isPkColumn;
    private Boolean isEmbedded = false;
    private Boolean isEnum = false;
    private Boolean isCompositePk = false;
    private Boolean isFkColumn = false;

    public ColumnInfoBuilder columnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public ColumnInfoBuilder attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public ColumnInfoBuilder attributeType(Class<?> attributeType) {
        this.attributeType = attributeType;
        return this;
    }

    public ColumnInfoBuilder isPkColumn(Boolean isPkColumn) {
        this.isPkColumn = isPkColumn;
        return this;
    }

    public ColumnInfoBuilder isEmbedded(Boolean isEmbedded) {
        this.isEmbedded = isEmbedded;
        return this;
    }

    public ColumnInfoBuilder isEnum(Boolean isEnum) {
        this.isEnum = isEnum;
        return this;
    }

    public ColumnInfoBuilder isCompositePk(Boolean isCompositePk) {
        this.isCompositePk = isCompositePk;
        return this;
    }

    public ColumnInfoBuilder isFKColumn(Boolean isFkColumn) {
        this.isFkColumn = isFkColumn;
        return this;
    }

    public ColumnInfo build() {
        return new ColumnInfo(columnName, attributeName, attributeType, isEmbedded, isEnum, isCompositePk, isPkColumn, isFkColumn);
    }
}
