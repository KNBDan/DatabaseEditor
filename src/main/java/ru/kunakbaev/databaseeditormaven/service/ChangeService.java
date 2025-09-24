package ru.kunakbaev.databaseeditormaven.service;

import org.springframework.stereotype.Service;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.TableChange;

@Service
public class ChangeService {

    private TableChange changes;

    public ChangeService() {
        this.changes = new TableChange();
    }

    public void newChangeModel(String tableName) {
        this.changes = new TableChange(tableName);
    }

    public void createColumn(Column column) {
        changes.createColumn(column);
    }

    public void deleteColumn(String delColumnName) {
        var modifiedColumns = changes.getModifiedColumns();
        for (Column column : modifiedColumns) {
            if (column.getName().equals(delColumnName)) {
                modifiedColumns.remove(column);
                changes.deleteColumn(column);
                return;
            }
        }

        var addedColumns = changes.getAddedColumns();
        for (Column column : addedColumns) {
            if (column.getName().equals(delColumnName)) {
                addedColumns.remove(column);
                return;
            }
        }
    }

    public void updateColumn(String oldName, Column updatedColumn) {
        var addedColumns = changes.getAddedColumns();
        var modifiedColumns = changes.getModifiedColumns();
        for (Column column : addedColumns) {
            if (column.getName().equals(oldName)) {
                addedColumns.remove(column);
                addedColumns.add(updatedColumn);
                return;
            }
        }
        for (Column column : modifiedColumns) {
            if (column.getName().equals(oldName)) {
                modifiedColumns.remove(column);
                modifiedColumns.add(updatedColumn);
                return;
            }
        }
        changes.updateColumn(updatedColumn);
    }

}
