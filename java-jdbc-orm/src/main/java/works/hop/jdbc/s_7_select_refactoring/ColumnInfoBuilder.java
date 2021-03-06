package works.hop.jdbc.s_7_select_refactoring;

public class ColumnInfoBuilder {

    private String columnName;
    private String attributeName;
    private Class<?> attributeType = String.class;
    private Boolean isPkColumn = false;
    private Boolean isEmbedded = false;
    private Boolean isEnum = false;
    private Boolean isCompositePk = false;
    private Boolean isFkColumn = false;
    private Boolean isCollection = false;
    private String inverseFkColumn;
    private String[][] compositeColumns = {};
    private Boolean isInverseComposite = false;
    private JoinTable joinTable;
    private String[] limitColumns = {};

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

    public ColumnInfoBuilder isCollection(Boolean isCollection) {
        this.isCollection = isCollection;
        return this;
    }

    public ColumnInfoBuilder compositeColumns(String[][] compositeColumns) {
        this.compositeColumns = compositeColumns;
        return this;
    }

    public ColumnInfoBuilder inverseFkColumn(String inverseFkColumn) {
        this.inverseFkColumn = inverseFkColumn;
        return this;
    }

    public ColumnInfoBuilder isInverseComposite(Boolean isInverseComposite) {
        this.isInverseComposite = isInverseComposite;
        return this;
    }

    public ColumnInfoBuilder joinTable(JoinTable joinTable) {
        this.joinTable = joinTable;
        return this;
    }

    public ColumnInfoBuilder limitColumns(String... limitColumns) {
        this.limitColumns = limitColumns;
        return this;
    }

    public ColumnInfo build() {
        return new ColumnInfo(columnName, attributeName, attributeType, isEmbedded, isEnum, isCompositePk, isPkColumn,
                isFkColumn, isCollection, inverseFkColumn, compositeColumns, isInverseComposite, joinTable, limitColumns);
    }
}
