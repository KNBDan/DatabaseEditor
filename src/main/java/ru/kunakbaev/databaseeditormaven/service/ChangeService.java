package ru.kunakbaev.databaseeditormaven.service;

import org.springframework.stereotype.Service;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.Change;
import ru.kunakbaev.databaseeditormaven.model.UpdateColumn;

@Service
public class ChangeService {

    private Change changes;

    public ChangeService() {
        this.changes = new Change();
    }

    public void newChangeModel(String tableName) {
        this.changes = new Change(tableName);
    }

    public void newChangeModel() {
        this.changes = new Change();
    }

    public void createColumn(Column createColumn) {
        var deletedColumns = changes.getDeletedColumns();

        for (Column column : deletedColumns) {
            if (column.getName().equals(createColumn.getName())) {
                deletedColumns.remove(column);
                changes.updateColumn(new UpdateColumn(column, createColumn));
                return;
            }
        }

        changes.createColumn(createColumn);
    }

    public Change getChanges() {
        return changes;
    }

    public void deleteColumn(Column deleteColumn) {
        var modifiedColumns = changes.getModifiedColumns();
        for (UpdateColumn updateColumn : modifiedColumns) {
            if (updateColumn.getUpdatedCondition().equals(deleteColumn)) {
                modifiedColumns.remove(updateColumn);
                changes.deleteColumn(updateColumn.getFirstCondition());
                return;
            }
        }

        var addedColumns = changes.getAddedColumns();
        for (Column column : addedColumns) {
            if (column.equals(deleteColumn)) {
                addedColumns.remove(column);
                return;
            }
        }
        changes.deleteColumn(deleteColumn);
    }

    public void updateColumn(Column oldColumn, Column updatedColumn) {
        var addedColumns = changes.getAddedColumns();
        var modifiedColumns = changes.getModifiedColumns();
        for (Column column : addedColumns) {
            if (column.equals(oldColumn)) {
                addedColumns.remove(column);
                addedColumns.add(updatedColumn);
                return;
            }
        }
        for (UpdateColumn updateColumnModel : modifiedColumns) {
            if (updateColumnModel.getUpdatedCondition().equals(oldColumn)) { // Если у записанного обновленной колонки имя равно имени изменяемой колонке
                modifiedColumns.remove(updateColumnModel); // То удаляем старую запись
                updateColumnModel.setUpdatedCondition(updatedColumn); // Меняем новую колонку в модели
                modifiedColumns.add(updateColumnModel); // Записываем обновленную иодель
                return;
            }
        }
        changes.updateColumn(new UpdateColumn(oldColumn, updatedColumn));
    }

}
