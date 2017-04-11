package com.ubb.mpp.client;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * @author Marius Adam
 */
@Configuration
public class ClientConfiguration {

    private ApplicationContext applicationContext;

    @Autowired
    public ClientConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("motocontest.wsdl");
        return marshaller;
    }

    @Bean
    public MotoContestClient contextClient(Jaxb2Marshaller marshaller) {
        MotoContestClient client = new MotoContestClient();
        client.setDefaultUri("http://localhost:8080/ws/moto-contest.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
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