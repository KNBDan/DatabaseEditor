package ru.kunakbaev.databaseeditormaven.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableChange {
    private String table;
    private String newPk;
    private List<Column> addedColumns;
    private List<Column> deletedColumns;
    private List<Column> modifiedColumns;

    public TableChange() {
        addedColumns = new ArrayList<>();
        deletedColumns = new ArrayList<>();
        modifiedColumns = new ArrayList<>();
    }

    public TableChange(String table) {
        this.table = table;
        addedColumns = new ArrayList<>();
        deletedColumns = new ArrayList<>();
        modifiedColumns = new ArrayList<>();
    }

    public void createColumn(Column column) {
        addedColumns.add(column);
    }

    public void deleteColumn(Column column) {
        deletedColumns.add(column);
    }

    public void updateColumn(Column column) {
        modifiedColumns.add(column);
    }

    public List<Column> getDeletedColumns() {
        return deletedColumns;
    }

    public void setDeletedColumns(List<Column> deletedColumns) {
        this.deletedColumns = deletedColumns;
    }

    public List<Column> getModifiedColumns() {
        return modifiedColumns;
    }

    public void setModifiedColumns(List<Column> modifiedColumns) {
        this.modifiedColumns = modifiedColumns;
    }

    public List<Column> getAddedColumns() {
        return addedColumns;
    }

    public void setAddedColumns(List<Column> addedColumns) {
        this.addedColumns = addedColumns;
    }

    public String getNewPk() {
        return newPk;
    }

    public void setNewPk(String newPk) {
        this.newPk = newPk;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
//create - just add names
//change - if name in create - rewrite create
//delete - check in create column name - just delete in create and done | or add name to delete and delete change by name
}
//METHOD - Column name - Column field - new variable //deprecated
