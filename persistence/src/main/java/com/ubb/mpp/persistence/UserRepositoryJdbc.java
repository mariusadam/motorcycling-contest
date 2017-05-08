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
public class UserRepositoryJdbc
        extends DbRepository<Integer, User>
        implements UserRepository {
    @Autowired
    public UserRepositoryJdbc(
            Adapter adapter,
            UserMapper mapper,
            @Value("${table.users}") String tableName) {
        super(adapter, mapper, tableName);
    }

    @Override
    public User findByEmail(String email) throws RepositoryException {
        return findOneBy("email", email);
    }
}
