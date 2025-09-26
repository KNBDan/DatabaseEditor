package ru.kunakbaev.databaseeditormaven.controller;

import org.springframework.stereotype.Controller;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.Table;
import ru.kunakbaev.databaseeditormaven.service.ChangeService;
import ru.kunakbaev.databaseeditormaven.service.DatabaseService;
import ru.kunakbaev.databaseeditormaven.service.SaveChangeLogService;
import ru.kunakbaev.databaseeditormaven.service.ui.TreeService;

import javax.swing.tree.TreeModel;
import java.sql.SQLException;

@Controller
public class MainFrameController {

    private final TreeService treeService;
    private final DatabaseService databaseService;
    private final ChangeService changeService;
    private final SaveChangeLogService saveChangeLogService;

    public MainFrameController(
            TreeService treeService,
            DatabaseService databaseService,
            ChangeService changeService,
            SaveChangeLogService saveChangeLogService
    ){
        this.treeService = treeService;
        this.databaseService = databaseService;
        this.changeService = changeService;
        this.saveChangeLogService = saveChangeLogService;
    }

    public TreeModel getTreeModel() throws SQLException {
        return treeService.createTreeModel();
    }

    public Table getTree(String name) throws SQLException {
        return databaseService.findTable(name);
    }

    public void setNewChangeModel(String tableName) {
        changeService.newChangeModel(tableName);  //ChangeLog create
    }

    public void saveDeletedColumn(String columnName) {
        changeService.deleteColumn(columnName); // ChangeLog Delete
    }

    public void saveUpdatedColumn(Column oldColumn, Column column) {
        changeService.updateColumn(oldColumn, column); // ChangeLog Update
    }

    public void saveNewColumn(Column column) {
        changeService.createColumn(column); //ChangeLog saveNewColumn
    }

    public void saveTable() throws SQLException {
        saveChangeLogService.GetChange(changeService.getChanges());
    }
}
