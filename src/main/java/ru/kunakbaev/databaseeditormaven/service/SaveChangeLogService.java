package ru.kunakbaev.databaseeditormaven.service;

import ru.kunakbaev.databaseeditormaven.model.Change;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.UpdateColumn;
import ru.kunakbaev.databaseeditormaven.repository.PostgresTableRepository;

import java.sql.SQLException;

public class SaveChangeLogService {

    private final PostgresTableRepository postgresTableRepository;

    public SaveChangeLogService(PostgresTableRepository postgresTableRepository) {
        this.postgresTableRepository = postgresTableRepository;
    }

    public void GetChange(Change change) throws SQLException {
        // update table name
        var tableName = change.getOldTableName();
        if (change.getNewTableName() != null) {
            postgresTableRepository.updateTableName(change.getOldTableName(), change.getNewTableName());
            tableName = change.getNewTableName();
        }
        //        delete
        var deletedColumns = change.getDeletedColumns();
        for (Column column : deletedColumns) {
            postgresTableRepository.deleteColumn(tableName, column);
        }
        //        update
        var modifiedColumns = change.getModifiedColumns();
        for (UpdateColumn updateColumnModel : modifiedColumns) {
            postgresTableRepository.updateColumn(tableName, updateColumnModel.getFirstCondition().getName(), updateColumnModel.getUpdatedCondition());
        }
        //        create
        var addedColumns = change.getAddedColumns();
        for (Column column : addedColumns) {
            postgresTableRepository.addColumn(tableName, column);
        }
    }

}
