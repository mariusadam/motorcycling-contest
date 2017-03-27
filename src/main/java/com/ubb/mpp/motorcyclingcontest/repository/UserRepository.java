package com.ubb.mpp.motorcyclingcontest.repository;

import com.ubb.mpp.motorcyclingcontest.domain.User;
import com.ubb.mpp.motorcyclingcontest.repository.mapper.UserMapper;
import com.ubb.mpp.motorcyclingcontest.service.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class UserRepository extends DbRepository<Integer,User> {
    @Autowired
    public UserRepository(
            Adapter adapter,
            UserMapper mapper,
            @Value("${table.users}") String tableName) {
        super(adapter, mapper, tableName);
    }

    public User findByEmail(String email) throws RepositoryException {
        return findOneBy("email", email);
    }
}
