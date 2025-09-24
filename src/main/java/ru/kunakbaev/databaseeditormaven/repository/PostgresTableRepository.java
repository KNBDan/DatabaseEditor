package ru.kunakbaev.databaseeditormaven.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PostgresTableRepository implements TableRepository {
    @Override
    public void createTable(String tableName, Map<String, String> column) {

    }

    @Override
    public void addColumn(String tableName, Map<String, String> column) {

    }

    @Override
    public void deleteColumn(String tableName, List<String> column) {

    }
}

