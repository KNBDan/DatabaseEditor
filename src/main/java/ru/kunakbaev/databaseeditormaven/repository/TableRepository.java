package ru.kunakbaev.databaseeditormaven.repository;

import ru.kunakbaev.databaseeditormaven.model.FieldType;

import java.util.List;
import java.util.Map;

public interface TableRepository {
    public void createTable(String tableName, Map<String, FieldType> column);

    public  void addColumn(String tableName, Map<String, FieldType> column);

    public  void deleteColumn(String tableName, List<String> column);
}
