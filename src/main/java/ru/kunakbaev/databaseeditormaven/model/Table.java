package ru.kunakbaev.databaseeditormaven.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private Column pkColumn;
    private List<Column> columns = new ArrayList<>();

    public Table() {}

    public Table(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Column getPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(Column pkColumn) {
        this.pkColumn = pkColumn;
    }
}
