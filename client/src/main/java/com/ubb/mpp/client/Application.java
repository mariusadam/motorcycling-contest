package com.ubb.mpp.client;

import motocontest.wsdl.LoginResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Marius Adam
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    CommandLineRunner lookup(MotoContestClient motoContestClient) {
        return args -> {
            LoginResponse response = motoContestClient.login("a@b.com", "abcd1234");
            System.err.println(response.getMessage());
        };
    }

}