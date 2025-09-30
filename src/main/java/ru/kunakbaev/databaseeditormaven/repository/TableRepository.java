package ru.kunakbaev.databaseeditormaven.repository;

import org.springframework.stereotype.Repository;
import ru.kunakbaev.databaseeditormaven.model.Column;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TableRepository {
    void createTable(Connection conn, String tableName, List<Column> columns) throws SQLException;

    void addColumn(Connection conn, String tableName, Column column) throws SQLException;

    void deleteColumn(Connection conn, String tableName, Column column) throws SQLException;

    void updateColumn(Connection conn, String tableName, String oldColumnName, Column newColumn) throws SQLException;

    void updateTableName(Connection conn, String oldTableName, String newTableName) throws SQLException;

    void deletePk(Connection conn, String tableName) throws SQLException;

    void setPk(Connection conn, String tableName, String newPk) throws SQLException;

    void deleteTable(Connection conn, String tableName) throws SQLException;
}