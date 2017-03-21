package com.ubb.mpp.motorcyclyingcontest;

import com.ubb.mpp.motorcyclyingcontest.config.DIConfiguration;
import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.DbRepository;
import com.ubb.mpp.motorcyclyingcontest.repository.Repository;
import com.ubb.mpp.motorcyclyingcontest.repository.mapper.UserMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.Properties;
import java.util.Random;

/**
 * @author Marius Adam
 */
public class Main {

    public static void main(String [] args) throws Exception {
        Properties dbProperties = new Properties();
        dbProperties.load(Main.class.getResourceAsStream("/config/database.properties"));

        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DIConfiguration.class);

        UserMapper userMapper = new UserMapper();
        Connection connection = DriverManager.getConnection(
                dbProperties.getProperty("url"),
                dbProperties.getProperty("user"),
                dbProperties.getProperty("pass")
        );
        Repository<Integer, User> userRepository = new DbRepository<>(connection, userMapper, "users");

        Random random = new SecureRandom();

        User u = new User();
        u.setFirstName("fname");
        u.setLastName("lname");
        u.setEmail(random.nextInt() + "aw@asdasdas.com");
        u.setPassword("this should be some hash");
        u.setSalt("a very random salt");
        u.setIsActive(true);
        u.setLoggedIn(false);

        userRepository.insert(u);

        Collection<User> all = userRepository.getAll();

        userRepository.getAll().forEach(System.out::println);
    }
}
