package com.ubb.mpp.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Marius Adam
 */
public class StartServer {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring.server.xml");
        System.out.println("Waiting for clients");
    }
}
