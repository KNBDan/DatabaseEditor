package ru.kunakbaev.databaseeditormaven.service;

import ru.kunakbaev.databaseeditormaven.configuration.PostgresConnection;

import java.sql.SQLException;
import java.util.List;

public class ConnectionService {
    private final PostgresConnection postgresConnection;

    public ConnectionService(PostgresConnection postgresConnection) {
        this.postgresConnection = postgresConnection;
    }

    public List<String> getConInfo() {
        return postgresConnection.getConnectionInfo();
    }

    public void setConnection(String url, String user, String password) throws SQLException {
        postgresConnection.updateConnection(url, user, password);
    }

    public boolean isConnection() {
        try {
            return postgresConnection.getConnection() != null && !postgresConnection.getConnection().isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

}
