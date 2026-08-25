package ru.kunakbaev.databaseeditormaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kunakbaev.databaseeditormaven.controller.MainFrameController;
import ru.kunakbaev.databaseeditormaven.frame.MainFrame;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DataBaseEditorMaven {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        System.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = SpringApplication.run(DataBaseEditorMaven.class, args);
        MainFrameController mainFrameController = context.getBean(MainFrameController.class);

        java.awt.EventQueue.invokeLater(() -> new MainFrame(mainFrameController).setVisible(true));
    }
}
