package ru.kunakbaev.databaseeditormaven.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private String name;
    private List<Table> tables = new ArrayList<>();

    public Database() {}

    public Database(String name) {
        setName(name);
    }

    public Database(String name, List<Table> tables) {
        this.name = name;
        this.tables = tables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
