package com.ubb.mpp.persistence;

import com.ubb.mpp.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class UserRepositoryHbn
        extends RepositoryHbn<Integer, User>
        implements UserRepository {

    @Autowired
    public UserRepositoryHbn(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    public User findByEmail(String email) throws RepositoryException {
        return findOneBy("email", email);
    }


}
