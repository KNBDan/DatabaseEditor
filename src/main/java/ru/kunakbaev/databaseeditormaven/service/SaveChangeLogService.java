package ru.kunakbaev.databaseeditormaven.service;

import org.springframework.stereotype.Service;
import ru.kunakbaev.databaseeditormaven.configuration.PostgresConnection;
import ru.kunakbaev.databaseeditormaven.model.Change;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.UpdateColumn;
import ru.kunakbaev.databaseeditormaven.repository.PostgresTableRepository;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class SaveChangeLogService {

    private final PostgresTableRepository postgresTableRepository;
    private final PostgresConnection postgresConnection;

    public SaveChangeLogService(PostgresTableRepository postgresTableRepository, PostgresConnection postgresConnection) {
        this.postgresTableRepository = postgresTableRepository;
        this.postgresConnection = postgresConnection;
    }

    public void deleteTable(String tableName) throws SQLException {
        postgresTableRepository.deleteTable(postgresConnection.getConnection(), tableName);
    }

    public void saveChange(Change change) throws SQLException {
        Connection conn = postgresConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            var tableName = change.getOldTableName();

            if (change.isNewTable()) {
                if (change.getNewTableName() != null) {
                    tableName = change.getNewTableName();
                }
                //  create new table
                postgresTableRepository.createTable(conn, tableName, change.getAddedColumns());
                conn.commit();
                return;
            } else {
                //  delete old pk
                postgresTableRepository.deletePk(conn, tableName);
                if (!change.getNewTableName().equals(change.getOldTableName())) {
                    //  update table name
                    postgresTableRepository.updateTableName(conn, change.getOldTableName(), change.getNewTableName());
                    tableName = change.getNewTableName();
                }
            }

            //  delete clumns
            var deletedColumns = change.getDeletedColumns();
            for (Column column : deletedColumns) {
                postgresTableRepository.deleteColumn(conn, tableName, column);
            }

            // update columns
            var modifiedColumns = change.getModifiedColumns();
            for (UpdateColumn updateColumnModel : modifiedColumns) {
                postgresTableRepository.updateColumn(conn, tableName, updateColumnModel.getFirstCondition().getName(), updateColumnModel.getUpdatedCondition());
            }

            //  add columns
            var addedColumns = change.getAddedColumns();
            for (Column column : addedColumns) {
                postgresTableRepository.addColumn(conn, tableName, column);
            }

            // set new pk
            var newPk = change.getNewPk();
            postgresTableRepository.setPk(conn, tableName, newPk);

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

}
