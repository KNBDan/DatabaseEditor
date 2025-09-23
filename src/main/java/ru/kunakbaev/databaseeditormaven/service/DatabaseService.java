package ru.kunakbaev.databaseeditormaven.service;

import org.springframework.stereotype.Service;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.Database;
import ru.kunakbaev.databaseeditormaven.model.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    private final Connection conn;

    public DatabaseService(Connection conn) {
        this.conn = conn;
    }

    public Database getDatabaseFromMeta() throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();

        Database database = new Database(conn.getCatalog());
        database.setTables(getTablesFromMeta(metaData));

        return  database;
    }

    private List<Table> getTablesFromMeta(DatabaseMetaData metaData) throws SQLException {
        List<Table> tables = new ArrayList<>();

        try (ResultSet tablesRs = metaData.getTables(
                null,
                null,
                "%",
                new String[]{"TABLE"})
        ) {
            while (tablesRs.next()) {
                String tableName = tablesRs.getString("TABLE_NAME");

                Table table = new Table(tableName);
                table.setColumns(getColumnFromMeta(metaData, tableName));

                tables.add(table);
            }
        }

        return  tables;
    }

    private List<Column> getColumnFromMeta(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<Column> columns = new ArrayList<>();

        try (ResultSet columnsRs = metaData.getColumns(
                null,
                null,
                tableName,
                "%")
        ) {
            while (columnsRs.next()) {
                Column column = new Column(
                        columnsRs.getString("COLUMN_NAME"),
                        columnsRs.getString("TYPE_NAME"),
                        columnsRs.getInt("COLUMN_SIZE"),
                        columnsRs.getBoolean("IS_NULLABLE")
                );
                columns.add(column);
            }
        }

        return columns;
    }

}
