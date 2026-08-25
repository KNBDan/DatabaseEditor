package ru.kunakbaev.databaseeditormaven.service.ui;

import org.springframework.stereotype.Service;
import ru.kunakbaev.databaseeditormaven.model.Column;
import ru.kunakbaev.databaseeditormaven.model.Table;
import ru.kunakbaev.databaseeditormaven.service.DatabaseService;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.sql.SQLException;

@Service
public class TreeService {

    private final DatabaseService databaseService;

    public TreeService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public DefaultTreeModel createTreeModel() throws SQLException {
        var database = databaseService.getDatabaseFromMeta();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(database.getName());

        for (Table table : database.getTables()) {
            DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(table.getName());

            for (Column column : table.getColumns()) {
                StringBuilder sb = new StringBuilder();
                String columnInfo = sb.append(column.getName()).append("  ").append(column.getType()).toString();
                DefaultMutableTreeNode columnNode = new DefaultMutableTreeNode(columnInfo);

                tableNode.add(columnNode);
            }

            root.add(tableNode);
        }

        return new DefaultTreeModel(root);
    }
}
