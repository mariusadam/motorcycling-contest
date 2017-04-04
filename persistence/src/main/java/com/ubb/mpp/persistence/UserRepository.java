package com.ubb.mpp.persistence;

import com.ubb.mpp.model.User;
import com.ubb.mpp.persistence.adapter.Adapter;
import com.ubb.mpp.persistence.mapper.UserMapper;
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
