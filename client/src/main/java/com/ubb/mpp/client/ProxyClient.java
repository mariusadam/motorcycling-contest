package com.ubb.mpp.client;

import com.ubb.mpp.model.*;
import com.ubb.mpp.services.ContestException;
import com.ubb.mpp.services.ContestServer;
import com.ubb.mpp.services.ObserverService;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

/**
 * @author Marius Adam
 */
@Component
public class ProxyClient implements ObserverService {
    private User user;
    private ContestServer server;
    private String uniqueId;

    public ProxyClient(ContestServer server) throws RemoteException {
        super();
        this.server = server;
        uniqueId = UUID.randomUUID().toString();
        user = null;
    }

    @Override
    public void contestantRegistered() throws RemoteException {
        //TODO refresh windows from here madafacka
    }

    @Override
    public String getObserverId() {
        return uniqueId;
    }

    public User login(String email, String password) throws ContestException {
        return server.login(email, password ,this);
    }

    public void logout(User user) throws ContestException {

    }

    public List<EngineCapacity> getEngineCapacities() {
        return null;
    }

    public List<Race> getRaces() {
        return null;
    }

    public List<String> suggestRaceNames(String text) {
        return null;
    }

    public List<Race> findRaces(String name, List<EngineCapacity> capacities) {
        return null;
    }

    public List<Contestant> getContestants() {
        return null;
    }

    public List<String> suggestTeamNames(String userText) {
        return null;
    }

    public List<Contestant> findContestants(String teamName) {
        return null;
    }
}