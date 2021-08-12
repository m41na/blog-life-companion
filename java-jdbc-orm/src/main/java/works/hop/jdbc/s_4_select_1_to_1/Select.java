package works.hop.jdbc.s_4_select_1_to_1;

import works.hop.jdbc.s_0_select.SelectResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Select {

    private static final String connectionString = "jdbc:h2:./data/sample-4.db";

    public static void main(String[] args) {
        SelectResult<Task> tasks = select("select * from tbl_task", new Object[]{}, EntityRegistry.registry.get(Task.class));
        if(tasks.error != null){
            System.out.println(tasks.error);
        }
        else {
            for (Task entity : tasks.result) {
                System.out.println(entity);
            }
        }
    }

    public static <T extends Entity> SelectResult<T> select(String query, Object[] parameters, EntityMetadata metadata){
        try(Connection conn = DriverManager.getConnection(connectionString)){
            try(PreparedStatement ps = conn.prepareStatement(query)){
                for(int i = 0; i < parameters.length; i++){
                    ps.setObject(i + 1, parameters[i]);
                }

                Collection<T> collection = new ArrayList<>();
                try(ResultSet rs = ps.executeQuery()){
                    ResultSetMetaData meta = rs.getMetaData();
                    extractEntities(rs, meta, metadata, collection);
                    return SelectResult.success(collection);
                }
                catch(Exception e){
                    return SelectResult.failure(e);
                }
            }
            catch(Exception e){
                return SelectResult.failure(e);
            }
        }
        catch(Exception e){
            return SelectResult.failure(e);
        }
    }

    private static <T extends Entity> void extractEntities(ResultSet rs, ResultSetMetaData meta, EntityMetadata metadata, Collection<T> collection) throws SQLException {
        while(rs.next()){
            T data  = extractEntity(rs, meta, metadata);
            collection.add(data);
        }
    }

    private static <T extends Entity> T extractEntity(ResultSet rs, ResultSetMetaData meta, EntityMetadata metadata) throws SQLException {
        T data = metadata.entityInstance();
        int numberOfColumns = meta.getColumnCount();
        //check mapped columns
        for(int i = 1; i <= numberOfColumns; i++){
            String columnName = meta.getColumnName(i).toLowerCase(); //important since the name might be in upper case
            Optional<ColumnInfo> columnInfoOption = metadata.resolveByColumnName(columnName);
            if(columnInfoOption.isPresent()) {
                ColumnInfo columnInfo = columnInfoOption.get();
                String attributeName = columnInfo.attributeName;
                Class<?> attributeType = columnInfo.isEnum ? String.class : columnInfo.attributeType;
                data.set(attributeName, rs.getObject(i, attributeType));
            }
        }
        //check for Embedded columns
        if(metadata.containsEmbedded()){
            for(ColumnInfo embeddedColumnInfo : metadata.embeddedColumns()){
                String attributeName = embeddedColumnInfo.attributeName;
                EntityMetadata embeddedEntityMetadata = EntityRegistry.registry.get(embeddedColumnInfo.attributeType);
                Object embeddedValue = extractEntity(rs, meta, embeddedEntityMetadata);
                data.set(attributeName, embeddedValue);
            }
        }
        //check for CompositePk columns
        if(metadata.containsCompositePk()){
            ColumnInfo compositePkColumnInfo = metadata.compositePkColumn(); //can only have one
            String attributeName = compositePkColumnInfo.attributeName;
            EntityMetadata compositePkEntityMetadata = EntityRegistry.registry.get(compositePkColumnInfo.attributeType);
            Object embeddedValue = extractEntity(rs, meta, compositePkEntityMetadata);
            data.set(attributeName, embeddedValue);
        }

        return data;
    }
}
