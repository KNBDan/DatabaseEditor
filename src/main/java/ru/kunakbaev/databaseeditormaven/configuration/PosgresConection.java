package ru.kunakbaev.databaseeditormaven.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class PosgresConection {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public Connection dataSource() throws SQLException {
        Connection conn = DriverManager.getConnection(
            DatabaseConfig.URL , DatabaseConfig.USER, DatabaseConfig.PASS
        );
        return conn;
    }
}
