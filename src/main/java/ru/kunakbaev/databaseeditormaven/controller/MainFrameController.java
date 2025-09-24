package ru.kunakbaev.databaseeditormaven.controller;

import ru.kunakbaev.databaseeditormaven.model.Table;
import ru.kunakbaev.databaseeditormaven.service.DatabaseService;
import ru.kunakbaev.databaseeditormaven.service.ui.TreeService;

import javax.swing.tree.TreeModel;

public class MainFrameController {

    private final TreeService treeService;
    private final DatabaseService databaseService;

    public MainFrameController(TreeService treeService, DatabaseService databaseService){
        this.treeService = treeService;
        this.databaseService = databaseService;
    }

    public TreeModel getTreeModel() {
        TreeModel tree;
        try {
            return treeService.createTreeModel();
        } catch (Exception e) {
//            logger.     
        }
        return null;
    }

    public Table getTree(String name) {
        try {
            return databaseService.findTable(name);
        } catch (Exception e) {

        }
        return null;
    }
}
