package com.ubb.mpp.server;

import com.ubb.mpp.model.User;
import com.ubb.mpp.services.ContestException;
import com.ubb.mpp.services.ContestServer;
import com.ubb.mpp.services.ObserverService;
import org.springframework.stereotype.Component;

/**
 * @author Marius Adam
 */
@Component
public class ContestServerImpl implements ContestServer {
    @Override
    public User login(String email, String password, ObserverService observer) throws ContestException {
        return null;
    }

    @Override
    public void logout(User user) throws ContestException {

    }
}
