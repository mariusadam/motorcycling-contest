package com.ubb.mpp.services;

import com.ubb.mpp.model.User;
import com.ubb.mpp.services.crud.ServerException;

/**
 * @author Marius Adam
 */
public interface ServerInterface {
    void login(User u, ClientInterface client) throws ServerException;
    void logout(User u, ClientInterface client) throws ServerException;
}
