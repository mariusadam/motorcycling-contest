package com.ubb.mpp.client;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

/**
 * @author Marius Adam
 */
@Configuration
@ImportResource("/spring-client.xml")
public class ClientConfiguration {

    private ApplicationContext applicationContext;

    @Autowired
    public ClientConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @Scope("prototype")
    public FXMLLoader getFXMLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
        return loader;
    }

//    @Bean
//    @Scope("singleton")
//    public Validator getJavaxValidator() {
//        return Validation.buildDefaultValidatorFactory().getValidator();
//    }
}