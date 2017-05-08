package com.ubb.mpp.persistence;

import com.ubb.mpp.model.User;

/**
 * @author Marius Adam
 */
public interface UserRepository extends Repository<Integer, User> {
    User findByEmail(String email) throws RepositoryException;
}
