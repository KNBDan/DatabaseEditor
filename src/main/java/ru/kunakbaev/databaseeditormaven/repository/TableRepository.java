package ru.kunakbaev.databaseeditormaven.repository;

import org.springframework.stereotype.Repository;
import ru.kunakbaev.databaseeditormaven.model.Column;

import java.sql.SQLException;
import java.util.List;

public interface TableRepository {
    public void createTable(String tableName, List<Column> columns) throws SQLException;

    public  void addColumn(String tableName, Column column) throws SQLException;

    public  void deleteColumn(String tableName, Column name) throws SQLException;

    public void updateColumn(String tableName, String oldColumnName, Column newColumn) throws SQLException;

    public void updateTableName(String oldTableName, String newTableName) throws SQLException;
}
