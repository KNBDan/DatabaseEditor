package ru.kunakbaev.databaseeditormaven.model;

import java.util.ArrayList;
import java.util.List;

public class Change {
    private boolean isNewTable;
    private String oldTableName;
    private String newTableName;
    private String newPk;
    private List<Column> addedColumns;
    private List<Column> deletedColumns;
    private List<UpdateColumn> modifiedColumns;

    public Change() {
        isNewTable = true;
        this.oldTableName = "New_Table";
        addedColumns = new ArrayList<>();
        deletedColumns = new ArrayList<>();
        modifiedColumns = new ArrayList<>();
    }

    public Change(String oldTableName) {
        isNewTable = false;
        this.oldTableName = oldTableName;
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

    public void updateColumn(UpdateColumn updateColumn) {
        modifiedColumns.add(updateColumn);
    }

    public List<Column> getDeletedColumns() {
        return deletedColumns;
    }

    public void setDeletedColumns(List<Column> deletedColumns) {
        this.deletedColumns = deletedColumns;
    }

    public List<UpdateColumn> getModifiedColumns() {
        return modifiedColumns;
    }

    public void setModifiedColumns(List<UpdateColumn> modifiedColumns) {
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

    public String getNewTableName() {
        return newTableName;
    }

    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }

    public String getOldTableName() {
        return oldTableName;
    }

    public void setOldTableName(String oldTableName) {
        this.oldTableName = oldTableName;
    }

    public boolean isNewTable() {
        return isNewTable;
    }

}
//create - just add names
//change - if name in create - rewrite create
//delete - check in create column name - just delete in create and done | or add name to delete and delete change by name


//METHOD - Column name - Column field - new variable //deprecated
