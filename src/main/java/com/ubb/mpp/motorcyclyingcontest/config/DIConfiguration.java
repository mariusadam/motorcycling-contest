package com.ubb.mpp.motorcyclyingcontest.config;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Marius Adam
 */
@Configuration
public class DIConfiguration {
    private ApplicationContext applicationContext;
    private ConfigurationService configurationService;

    @Autowired
    public DIConfiguration(ApplicationContext applicationContext, ConfigurationService configurationService) {
        this.applicationContext = applicationContext;
        this.configurationService = configurationService;
    }

    @Bean
    @Scope("prototype")
    public Connection getNewConnection() throws Exception {
        return DriverManager.getConnection(
                configurationService.getDatabaseUrl(),
                configurationService.getDatabaseUser(),
                configurationService.getDatabasePass()
        );
    }

    @Bean
    @Scope("prototype")
    public FXMLLoader getFXMLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
        return loader;
    }

    @Bean
    @Scope("singleton")
    public Validator getJavaxValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
