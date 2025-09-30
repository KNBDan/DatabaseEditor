package ru.kunakbaev.databaseeditormaven.controller;

import org.springframework.stereotype.Controller;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.Table;
import ru.kunakbaev.databaseeditormaven.service.ChangeService;
import ru.kunakbaev.databaseeditormaven.service.ConnectionService;
import ru.kunakbaev.databaseeditormaven.service.DatabaseService;
import ru.kunakbaev.databaseeditormaven.service.SaveChangeLogService;
import ru.kunakbaev.databaseeditormaven.service.ui.TreeService;

import javax.swing.tree.TreeModel;
import java.sql.SQLException;
import java.util.List;

@Controller
public class MainFrameController {

    private final TreeService treeService;
    private final DatabaseService databaseService;
    private final ChangeService changeService;
    private final SaveChangeLogService saveChangeLogService;
    private final ConnectionService connectionService;

    public MainFrameController(
            TreeService treeService,
            DatabaseService databaseService,
            ChangeService changeService,
            SaveChangeLogService saveChangeLogService,
            ConnectionService connectionService
    ){
        this.treeService = treeService;
        this.databaseService = databaseService;
        this.changeService = changeService;
        this.saveChangeLogService = saveChangeLogService;
        this.connectionService = connectionService;
    }

    public TreeModel getTreeModel() throws SQLException {
        return treeService.createTreeModel();
    }

    public Table getTree(String name) throws SQLException {
        return databaseService.findTable(name);
    }

    public void setNewChangeModel(String tableName) throws SQLException {
        changeService.newChangeModel(tableName);  //ChangeLog open table
    }

    public void setNewChangeModel() {
        changeService.newChangeModel();  //ChangeLog create new table
    }

    public boolean isConnection(){
        return connectionService.isConnection();
    }

    public void saveDeletedColumn(Column column) {
        changeService.deleteColumn(column); // ChangeLog Delete
    }

    public void saveUpdatedColumn(Column oldColumn, Column column) {
        changeService.updateColumn(oldColumn, column); // ChangeLog Update
    }

    public void saveNewColumn(Column column) {
        changeService.createColumn(column); //ChangeLog Add
    }

    public void saveTable() throws SQLException {
        saveChangeLogService.saveChange(changeService.getChanges());
    }

    public void deleteTable(String tableName) throws SQLException{
        saveChangeLogService.deleteTable(tableName);
    }

    public List<String> getConInfo() {
        return connectionService.getConInfo();
    }

    public void setConnection(String url, String user, String password) throws SQLException {
        connectionService.setConnection(url, user, password);
    }

    public String getPk() {
        return changeService.getChanges().getNewPk();
    }

    public void setPk(String pk) {
        changeService.getChanges().setNewPk(pk);
    }

    public void setNewTableName(String tableName) {
        changeService.getChanges().setNewTableName(tableName);
    }

}
