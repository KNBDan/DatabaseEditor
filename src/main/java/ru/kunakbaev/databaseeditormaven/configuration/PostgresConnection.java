package ru.kunakbaev.databaseeditormaven.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgresConnection {

        Connection conn;
    private final DbConfigManager dbConfigManager;

    public PostgresConnection(DbConfigManager dbConfigManager) {
        this.dbConfigManager = dbConfigManager;
    }

    public void setNewConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }

        conn = dataSource();
    }

    public Connection getConnection() {
        return conn;
    }

    private Connection dataSource() throws SQLException {
        List<String> conInfo = getConnectionInfo();
        return DriverManager.getConnection(
                conInfo.get(0),
                conInfo.get(1),
                conInfo.get(2)
        );
    }

    public void updateConnection(String url, String user, String password) throws SQLException {
        dbConfigManager.update(url, user, password);
        setNewConnection();
    }

    public List<String> getConnectionInfo() {
        return List.of(
                dbConfigManager.getUrl(),
                dbConfigManager.getUsername(),
                dbConfigManager.getPassword()
        );
    }

    public void executeSQL(String sql) throws SQLException {
        try (Connection conn = dataSource();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}