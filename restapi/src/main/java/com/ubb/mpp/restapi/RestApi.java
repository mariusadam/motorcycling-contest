package com.ubb.mpp.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Marius Adam
 */
@SpringBootApplication
@ComponentScan("com.ubb.mpp.restapi")
@ImportResource("classpath:persistence-config.xml")
public class RestApi {
    public static void main(String ...args) {
        SpringApplication.run(RestApi.class, args);
    }
}
