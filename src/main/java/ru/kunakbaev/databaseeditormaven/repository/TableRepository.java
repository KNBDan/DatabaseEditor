package ru.kunakbaev.databaseeditormaven.repository;

import java.util.List;
import java.util.Map;

public interface TableRepository {
    public void createTable(String tableName, Map<String, String> column);

    public  void addColumn(String tableName, Map<String, String> column);

    public  void deleteColumn(String tableName, List<String> column);
}
