package com.ubb.mpp.motorcyclyingcontest.config;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author Marius Adam
 */
@Configuration
public class DIConfiguration {
    private Properties dbProperties;
    private ApplicationContext applicationContext;

    @Autowired
    public DIConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @Scope("prototype")
    public Connection getNewConnection() throws Exception {
        setDbProperties();
        return DriverManager.getConnection(
                dbProperties.getProperty("url"),
                dbProperties.getProperty("user"),
                dbProperties.getProperty("pass")
        );
    }

    @Bean
    @Scope("prototype")
    public FXMLLoader getFXMLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
        return loader;
    }

    private void setDbProperties() throws IOException {
        if (dbProperties != null) {
            return;
        }

        dbProperties = new Properties();
        dbProperties.load(getClass().getResourceAsStream("/config/database.properties"));
    }
}
