package works.hop.jdbc.s_8_validate_metadata;

public class TablePkColumn {

    final String columnName;
    final String pkName;
    final int keySeq;

    public TablePkColumn(String columnName, int keySeq) {
        this(columnName, keySeq, null);
    }

    public TablePkColumn(String columnName, int keySeq, String pkName) {
        this.columnName = columnName;
        this.keySeq = keySeq;
        this.pkName = pkName;
    }

    @Override
    public String toString() {
        return "TablePkColumn{\n" +
                "\t\t\tcolumnName='" + columnName + "'\n" +
                "\t\t\tpkName='" + pkName + "'\n" +
                "\t\t\tkeySeq=" + keySeq + "\n" +
                "\t\t}";
    }
}
