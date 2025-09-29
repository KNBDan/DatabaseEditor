package ru.kunakbaev.databaseeditormaven.service;

import org.springframework.transaction.annotation.Transactional;
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

    public void deleteTable(String tableName) throws SQLException {
        postgresTableRepository.deleteTable(tableName);
    }

    @Transactional(rollbackFor = SQLException.class)
    public void saveChange(Change change) throws SQLException {
        // update table name
        var tableName = change.getOldTableName();

        if (change.isNewTable()) {
            // create new table
            if (change.getNewTableName() != null) {
                tableName = change.getNewTableName();
            }
            postgresTableRepository.createTable(tableName, change.getAddedColumns());
            return;
        } else {
            if (!change.getNewTableName().equals(change.getOldTableName())) {
                postgresTableRepository.updateTableName(change.getOldTableName(), change.getNewTableName());
                tableName = change.getNewTableName();
            }
        }
        //      unlink pk
        try {
            postgresTableRepository.deletePk(change.getOldTableName());
        } catch (SQLException e) {
            System.out.println("EXCEPTION PK DELETE: " + e);
        }
        //      delete
        var deletedColumns = change.getDeletedColumns();
        for (Column column : deletedColumns) {
            postgresTableRepository.deleteColumn(tableName, column);
        }
        //      update
        var modifiedColumns = change.getModifiedColumns();
        for (UpdateColumn updateColumnModel : modifiedColumns) {
            postgresTableRepository.updateColumn(tableName, updateColumnModel.getFirstCondition().getName(), updateColumnModel.getUpdatedCondition());
        }
        //      create
        var addedColumns = change.getAddedColumns();
        for (Column column : addedColumns) {
            postgresTableRepository.addColumn(tableName, column);
        }

        //      link new p
        var newPk = change.getNewPk();
        postgresTableRepository.setPk(tableName, newPk);
    }

}
