package ru.kunakbaev.databaseeditormaven.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
        return DriverManager.getConnection(
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