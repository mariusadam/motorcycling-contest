package com.ubb.mpp.motorcyclyingcontest;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.DbRepository;
import com.ubb.mpp.motorcyclyingcontest.repository.Repository;
import com.ubb.mpp.motorcyclyingcontest.repository.mapper.UserMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * Created by marius on 07.03.2017.
 */
public class Main {

    public static void main(String [] args) throws Exception {
        Properties dbProperties = new Properties();
        dbProperties.load(Main.class.getResourceAsStream("/config/database.properties"));

        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        UserMapper userMapper = new UserMapper();
        Connection connection = DriverManager.getConnection(
                dbProperties.getProperty("url"),
                dbProperties.getProperty("user"),
                dbProperties.getProperty("pass")
        );
        Repository<Integer, User> userRepository = new DbRepository<>(connection, userMapper, "users");

        Random random = new SecureRandom();

        User u = new User();
        u
                .setFirstName("fname")
                .setLastName("lname")
                .setEmail(random.nextInt() + "aw@asdasdas.com")
                .setPassword("this should be some hash")
                .setSalt("a very random salt")
                .setIsActive(true)
                .setLoggedIn(false);

        userRepository.insert(u);

        Collection<User> all = userRepository.getAll();

        userRepository.getAll().forEach(System.out::println);
    }
}
