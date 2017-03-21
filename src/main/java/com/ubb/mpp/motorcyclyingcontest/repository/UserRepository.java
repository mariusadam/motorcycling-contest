package com.ubb.mpp.motorcyclyingcontest.repository;

import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class UserRepository extends DbRepository<Integer, User> {
    private static final String USERS_TABLE = "users";

    @Autowired
    public UserRepository(Connection connection, UserMapper mapper) {
        super(connection, mapper, USERS_TABLE);
    }
}
