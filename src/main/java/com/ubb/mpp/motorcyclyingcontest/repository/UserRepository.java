package com.ubb.mpp.motorcyclyingcontest.repository;

import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class UserRepository extends DbRepository<Integer, User> {
    @Autowired
    public UserRepository(
            Connection connection,
            UserMapper mapper,
            @Value("${table.users}") String tableName) {
        super(connection, mapper, tableName);
    }

    public User findByEmail(String email) throws RepositoryException {
        Map<String, String> filter = new HashMap<>();
        filter.put("email", email);
        return findBy(filter).iterator().next();
    }
}
