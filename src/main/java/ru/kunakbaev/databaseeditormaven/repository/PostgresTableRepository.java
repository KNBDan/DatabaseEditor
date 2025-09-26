package ru.kunakbaev.databaseeditormaven.repository;

import ru.kunakbaev.databaseeditormaven.configuration.PostgresConnection;
import ru.kunakbaev.databaseeditormaven.model.Column;

import java.sql.SQLException;
import java.util.List;

public class PostgresTableRepository implements TableRepository {
    private final PostgresConnection postgresConnection;

    public PostgresTableRepository(PostgresConnection postgresConnection) {
        this.postgresConnection = postgresConnection;
    }

    @Override
    public void createTable(String tableName, List<Column> columns) throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE ")
                .append(tableName).append(" (");

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (i > 0) sql.append(", ");
            sql.append(column.getName()).append(" ").append(column.getType());
            if ("varchar".equals(column.getType())) {
                sql.append("(").append(column.getSize()).append(")");
            }
            if (!column.isNullable()) {
                sql.append(" NOT NULL");
            }
        }
        sql.append(")");

        postgresConnection.executeSQL(sql.toString());
    }

    @Override
    public void addColumn(String tableName, Column column) throws SQLException {
        String sql = "ALTER TABLE " + tableName + " ADD COLUMN " +
                column.getName() + " " + column.getType();

        if ("varchar".equals(column.getType())) {
            sql += "(" + column.getSize() + ")";
        }
        if (!column.isNullable()) {
            sql += " NOT NULL";
        }

        postgresConnection.executeSQL(sql);
    }

    @Override
    public void deleteColumn(String tableName, Column column) throws SQLException {
        String sql = "ALTER TABLE " + tableName + " DROP COLUMN " + column.getName();
        postgresConnection.executeSQL(sql);
    }

    @Override
    public void updateColumn(String tableName, String oldColumnName, Column newColumn) throws SQLException {
        // Переименование
        if (!oldColumnName.equals(newColumn.getName())) {
            String renameSql = "ALTER TABLE " + tableName + " RENAME COLUMN " +
                    oldColumnName + " TO " + newColumn.getName();
            postgresConnection.executeSQL(renameSql);
        }

        // Изменение типа
        String alterTypeSql = "ALTER TABLE " + tableName + " ALTER COLUMN " +
                newColumn.getName() + " TYPE " + newColumn.getType();

        if ("varchar".equals(newColumn.getType())) {
            alterTypeSql += "(" + newColumn.getSize() + ")";
        }

        postgresConnection.executeSQL(alterTypeSql);

        // Изменение NULLABLE
        String nullableSql = "ALTER TABLE " + tableName + " ALTER COLUMN " +
                newColumn.getName() + (newColumn.isNullable() ? " DROP NOT NULL" : " SET NOT NULL");
        postgresConnection.executeSQL(nullableSql);
    }

    @Override
    public void updateTableName(String oldTableName, String newTableName) throws SQLException {
        String sql = "ALTER TABLE " + oldTableName + " RENAME TO " + newTableName;
        postgresConnection.executeSQL(sql);
    }

    private String buildColumnDefinition(Column column) {
        String definition = column.getName() + " " + column.getType();

        if ("varchar".equals(column.getType())) {
            definition += "(" + column.getSize() + ")";
        }

        if (!column.isNullable()) {
            definition += " NOT NULL";
        }

        return definition;
    }
}