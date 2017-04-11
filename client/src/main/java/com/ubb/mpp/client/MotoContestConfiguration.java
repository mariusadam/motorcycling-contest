package com.ubb.mpp.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * @author Marius Adam
 */
@Configuration
public class MotoContestConfiguration {

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

}