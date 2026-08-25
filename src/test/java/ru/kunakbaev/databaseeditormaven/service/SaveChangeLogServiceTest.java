package ru.kunakbaev.databaseeditormaven.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kunakbaev.databaseeditormaven.configuration.PostgresConnection;
import ru.kunakbaev.databaseeditormaven.model.Change;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.UpdateColumn;
import ru.kunakbaev.databaseeditormaven.repository.PostgresTableRepository;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SaveChangeLogServiceTest {
    @Mock
    private PostgresTableRepository postgresTableRepository;
    @Mock
    private PostgresConnection postgresConnection;
    @Mock
    private Connection connection;
    @InjectMocks
    private SaveChangeLogService saveChangeLogService;

    @BeforeEach
    void mockUp() {
        MockitoAnnotations.initMocks(this);
        when(postgresConnection.getConnection()).thenReturn(connection);
    }

    @Test
    void saveChangeNewTable() throws SQLException {
        Change change = new Change();
        change.setNewTableName("test_name");
        Column column = new Column("id","int4",0,false);
        change.createColumn(column);

        saveChangeLogService.saveChange(change);

        verify(postgresTableRepository).createTable(connection,"test_name",change.getAddedColumns());
        verify(connection).commit();
    }

    @Test
    void saveChangeExistTable() throws SQLException {
        Change change = new Change("old_table");
        change.setNewTableName("old_table");
        Column column = new Column("number","int4",0,false);
        change.createColumn(column);

        saveChangeLogService.saveChange(change);

        verify(postgresTableRepository).addColumn(connection,"old_table", column);
        verify(connection).commit();
    }

    @Test
    void saveChangeDeleteColumnInExistTable() throws SQLException {
        Change change = new Change("old_table");
        change.setNewTableName("old_table");
        Column column = new Column("number","int4",0,false);
        change.deleteColumn(column);

        saveChangeLogService.saveChange(change);

        verify(postgresTableRepository).deleteColumn(connection,"old_table", column);
        verify(connection).commit();
    }

    @Test
    void saveChangeUpdateColumnInExistTable() throws SQLException {
        Change change = new Change("old_table");
        change.setNewTableName("old_table");
        Column oldColumn = new Column("number","int4",0,false);
        Column column = new Column("new_number","int4",0,true);
        change.updateColumn(new UpdateColumn(oldColumn,column));

        saveChangeLogService.saveChange(change);

        verify(postgresTableRepository).updateColumn(connection,"old_table", oldColumn.getName(), column);
        verify(connection).commit();
    }

    @Test
    void saveChangeNewPkInExistTable() throws SQLException {
        Change change = new Change("old_table");
        change.setNewTableName("old_table");
        Column column = new Column("id","int4",0,false);
        change.createColumn(column);
        change.setNewPk("id");

        saveChangeLogService.saveChange(change);

        verify(postgresTableRepository).addColumn(connection,"old_table", column);
        verify(postgresTableRepository).setPk(connection,"old_table", "id");
        verify(connection).commit();
    }

}
