package com.ubb.mpp.server;

import com.ubb.mpp.persistence.RepositoryException;
import com.ubb.mpp.server.domain.LoginRequest;
import com.ubb.mpp.server.domain.LoginResponse;
import com.ubb.mpp.server.domain.User;
import com.ubb.mpp.services.crud.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author Marius Adam
 */
@Endpoint
public class MotoContestEndpoint {
    private static final String NAMESPACE_URI = "http://moto-contest.org/api";

    private UserService userService;

    @Autowired
    public MotoContestEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest request) {
        LoginResponse response = new LoginResponse();
        com.ubb.mpp.model.User u;
        try {
            u = userService.getUserByEmailAndPassword(
                    request.getEmail(),
                    request.getPassword()
            );
        } catch (RepositoryException ex) {
            response.setMessage(ex.getMessage());
            return response;
        }

        if (u == null) {
            response.setMessage("Invalid username of password");
        } else {
            response.setMessage("OK");
            User loggedUser = new User();
            loggedUser.setEmail(u.getEmail());
            loggedUser.setFirstName(u.getFirstName());
            loggedUser.setLastName(u.getLastName());
            response.setUser(loggedUser);
        }

        return response;
    }
}