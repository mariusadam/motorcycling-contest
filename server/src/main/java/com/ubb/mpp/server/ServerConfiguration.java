package com.ubb.mpp.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Marius Adam
 */
@Configuration
public class ServerConfiguration {
    @Bean
    public Connection getConnection(
//            @Value("${rabbitmq.host}") String host,
//            @Value("${rabbitmq.vhost}") String vHost,
//            @Value("${rabbitmq.port}") int port,
//            @Value("${rabbitmq.user}") String user,
//            @Value("${rabbitmq.pass}") String pass
    ) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("rabbitmq_vhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("java_rmq_user");
        connectionFactory.setPassword("java_rmq_pass");

        return connectionFactory.newConnection();
    }

    @Bean
    public Channel createRmqChannel(Connection connection) throws IOException {
        return connection.createChannel();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer swaggerProperties() {
        PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
        p.setIgnoreUnresolvablePlaceholders(true);
        return p;
    }
}
