package ru.kunakbaev.databaseeditormaven.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kunakbaev.databaseeditormaven.model.Change;
import ru.kunakbaev.databaseeditormaven.model.Column;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeServiceTest {
    private ChangeService changeService;

    @BeforeEach
    void setChangeService(){
        changeService = new ChangeService();
    }

    @Test
    void createColumnTest() {
        Column column = new Column("Test", "int4", 0, false);
        changeService.createColumn(column);

        Change changes = changeService.getChanges();
        assertTrue(changes.getAddedColumns().contains(column));
        assertEquals(1, changes.getAddedColumns().size());
    }

    @Test
    void deleteCreatedBeforeColumnTest() {
        Column column = new Column("Test", "int4", 0, false);
        changeService.createColumn(column);
        changeService.deleteColumn(column);

        Change changes = changeService.getChanges();
        assertFalse(changes.getAddedColumns().contains(column));
        assertEquals(0, changes.getAddedColumns().size());
        assertFalse(changes.getDeletedColumns().contains(column));
        assertEquals(0, changes.getDeletedColumns().size());
    }

    @Test
    void deleteSavedBeforeColumnTest() {
        Column column = new Column("Test", "int4", 0, false);
        changeService.deleteColumn(column);

        Change changes = changeService.getChanges();
        assertTrue(changes.getDeletedColumns().contains(column));
        assertEquals(1, changes.getDeletedColumns().size());
    }

    @Test
    void updateColumnTest() {
        Column column = new Column("Test", "int4", 0, false);
        Column newColumn = new Column("newTest", "varchar", 255, true);
        changeService.createColumn(column);
        changeService.updateColumn(column, newColumn);

        Change changes = changeService.getChanges();
        assertTrue(changes.getAddedColumns().contains(newColumn));
        assertFalse(changes.getAddedColumns().contains(column));
        assertEquals(1, changes.getAddedColumns().size());
    }

}
