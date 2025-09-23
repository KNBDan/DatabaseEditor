package ru.kunakbaev.databaseeditormaven.repository;

import ru.kunakbaev.databaseeditormaven.model.FieldType;

import java.util.List;
import java.util.Map;

public class PostgresTableRepository implements TableRepository {
    @Override
    public void createTable(String tableName, Map<String, FieldType> column) {

    }

    @Override
    public void addColumn(String tableName, Map<String, FieldType> column) {

    }

    @Override
    public void deleteColumn(String tableName, List<String> column) {

    }
}

