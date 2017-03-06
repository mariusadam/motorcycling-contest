package com.ubb.mpp.motorcyclyingcontest;

import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.DbRepository;
import com.ubb.mpp.motorcyclyingcontest.repository.Repository;
import com.ubb.mpp.motorcyclyingcontest.repository.mapper.UserMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by marius on 07.03.2017.
 */
public class Main {

    public static void main(String [] args) throws Exception {
        Properties dbProperties = new Properties();
        dbProperties.load(Main.class.getResourceAsStream("/config/database.properties"));

        Class<?> cl = Class.forName("com.mysql.jdbc.Driver");
        UserMapper userMapper = new UserMapper();
        Connection connection = DriverManager.getConnection(
                dbProperties.getProperty("url"),
                dbProperties.getProperty("user"),
                dbProperties.getProperty("pass")
        );
        Repository<Integer, User> userRepository = new DbRepository<>(connection, userMapper, "users");
    }
}
