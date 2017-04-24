package com.ubb.mpp.services;

import com.ubb.mpp.model.User;

/**
 * @author Marius Adam
 */
public interface ContestServer {
    User login(String email, String password, ObserverService observer) throws ContestException;
    void logout(User user) throws ContestException;
}
