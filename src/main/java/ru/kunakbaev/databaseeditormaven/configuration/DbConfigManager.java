package ru.kunakbaev.databaseeditormaven.configuration;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@Component
public class DbConfigManager {

    private static final String CONFIG_FILE = "db-config.properties";
    private final Properties properties = new Properties();

    public DbConfigManager() {
        load();
    }

    public void load() {
        try {
            Path path = Path.of(CONFIG_FILE);
            if (Files.exists(path)) {
                try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
                    properties.load(fis);
                }
            } else {
                properties.setProperty("url", "jdbc:postgresql://localhost:5432/");
                properties.setProperty("username", "postgres");
                properties.setProperty("password", "1234");
                save();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Database connection settings");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return properties.getProperty("url");
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

    public void update(String url, String username, String password) {
        properties.setProperty("url", url);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        save();
    }
}

