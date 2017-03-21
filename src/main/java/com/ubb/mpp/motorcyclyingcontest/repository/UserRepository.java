package com.ubb.mpp.motorcyclyingcontest.repository;

import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.mapper.Mapper;

import java.sql.Connection;

/**
 * @author Marius Adam
 */
public class UserRepository extends DbRepository<Integer, User> {
    public UserRepository(Connection connection, Mapper<User> mapper, String tableName) {
        super(connection, mapper, tableName);
    }
}
