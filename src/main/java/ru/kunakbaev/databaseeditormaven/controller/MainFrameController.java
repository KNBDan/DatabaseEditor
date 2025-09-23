package ru.kunakbaev.databaseeditormaven.controller;

import ru.kunakbaev.databaseeditormaven.service.ui.TreeService;

import javax.swing.tree.TreeModel;

public class MainFrameController {

    private final TreeService treeService;

    public MainFrameController(TreeService treeService){
        this.treeService = treeService;
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
}
