package works.hop.jdbc.s_8_validate_metadata;

public class TableFkColumn {

    final String pkTableName;
    final String pkColumnName;
    final String pkName;
    final String fkTableName;
    final String fkColumnName;
    final String fkName;
    final int keySeq;

    public TableFkColumn(String pkTableName, String pkColumnName, String fkTableName, String fkColumnName, int keySeq) {
        this(pkTableName, pkColumnName, null, fkTableName, fkColumnName, null, keySeq);
    }

    public TableFkColumn(String pkTableName, String pkColumnName, String pkName, String fkTableName, String fkColumnName, String fkName, int keySeq) {
        this.pkTableName = pkTableName;
        this.pkColumnName = pkColumnName;
        this.pkName = pkName;
        this.fkTableName = fkTableName;
        this.fkColumnName = fkColumnName;
        this.fkName = fkName;
        this.keySeq = keySeq;
    }

    @Override
    public String toString() {
        return "TableFkColumn{\n" +
                "\t\t\tpkTableName='" + pkTableName + "'\n" +
                "\t\t\tpkColumnName='" + pkColumnName + "'\n" +
                "\t\t\tpkName='" + pkName + "'\n" +
                "\t\t\tfkTableName='" + fkTableName + "'\n" +
                "\t\t\tfkColumnName='" + fkColumnName + "'\n" +
                "\t\t\tfkName='" + fkName + "'\n" +
                "\t\t\tkeySeq=" + keySeq + "\n" +
                "\t\t}";
    }
}
