package ru.kunakbaev.databaseeditormaven.repository;

import org.springframework.stereotype.Repository;
import ru.kunakbaev.databaseeditormaven.model.Column;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class PostgresTableRepository implements TableRepository {

    @Override
    public void createTable(Connection conn, String tableName, List<Column> columns) throws SQLException {
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

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql.toString());
        }
    }

    @Override
    public void addColumn(Connection conn, String tableName, Column column) throws SQLException {
        String sql = "ALTER TABLE " + tableName + " ADD COLUMN " +
                column.getName() + " " + column.getType();

        if ("varchar".equals(column.getType())) {
            sql += "(" + column.getSize() + ")";
        }
        if (!column.isNullable()) {
            sql += " NOT NULL";
        }

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void deleteColumn(Connection conn, String tableName, Column column) throws SQLException {
        String sql = "ALTER TABLE " + tableName + " DROP COLUMN " + column.getName();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void updateColumn(Connection conn, String tableName, String oldColumnName, Column newColumn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            if (!oldColumnName.equals(newColumn.getName())) {
                String renameSql = "ALTER TABLE " + tableName + " RENAME COLUMN " +
                        oldColumnName + " TO " + newColumn.getName();
                stmt.execute(renameSql);
            }

            String alterTypeSql = "ALTER TABLE " + tableName + " ALTER COLUMN " +
                    newColumn.getName() + " TYPE " + newColumn.getType();

            if ("varchar".equals(newColumn.getType())) {
                alterTypeSql += "(" + newColumn.getSize() + ")";
            }
            stmt.execute(alterTypeSql);

            String nullableSql = "ALTER TABLE " + tableName + " ALTER COLUMN " +
                    newColumn.getName() + (newColumn.isNullable() ? " DROP NOT NULL" : " SET NOT NULL");
            stmt.execute(nullableSql);
        }
    }

    @Override
    public void updateTableName(Connection conn, String oldTableName, String newTableName) throws SQLException {
        String sql = "ALTER TABLE " + oldTableName + " RENAME TO " + newTableName;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void deletePk(Connection conn, String tableName) throws SQLException {
        String sql = "ALTER TABLE " + tableName + " DROP CONSTRAINT IF EXISTS " + tableName + "_pkey";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void setPk(Connection conn, String tableName, String newPk) throws SQLException {
        String makeNotNullSql = "ALTER TABLE " + tableName + " ALTER COLUMN " + newPk + " SET NOT NULL";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(makeNotNullSql);
        }

        String setPkSql = "ALTER TABLE " + tableName + " ADD PRIMARY KEY (" + newPk + ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(setPkSql);
        }
    }

    @Override
    public void deleteTable(Connection conn, String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName + " CASCADE";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}