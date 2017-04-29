package com.ubb.mpp.client;

import com.ubb.mpp.client.controller.Updatable;
import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.model.Race;
import com.ubb.mpp.model.User;
import com.ubb.mpp.services.ContestException;
import com.ubb.mpp.services.ContestServer;
import com.ubb.mpp.services.ObserverService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Marius Adam
 */
public class ProxyClient extends UnicastRemoteObject implements ObserverService, Serializable {
    private User user;
    private ContestServer server;
    private String uniqueId;
    private transient List<Updatable> windows;

    public ProxyClient(ContestServer server) throws RemoteException {
        this.server = server;
        uniqueId = UUID.randomUUID().toString();
        user = null;
        windows = new ArrayList<>();
    }

    public void addWindow(Updatable w) {
        windows.add(w);
    }

    @Override
    public void contestantRegistered() throws RemoteException {
        windows.forEach(Updatable::update);
    }

    @Override
    public String getObserverId() {
        return uniqueId;
    }

    public void login(String email, String password)  {
        try {
            user = server.login(email, password ,this);
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout()  {
        try {
            server.logout(user);
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EngineCapacity> getEngineCapacities()  {
        try {
            return server.getEngineCapacities();
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Race> getRaces()  {
        try {
            return server.getRaces();
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> suggestRaceNames(String text){
        try {
            return server.suggestRaceNames(text);
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Race> findRaces(String name, List<EngineCapacity> capacities)  {
        try {
            return server.findRaces(name, capacities);
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contestant> getContestants()  {
        try {
            return server.getContestants();
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> suggestTeamNames(String userText)  {
        try {
            return server.suggestTeamNames(userText);
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contestant> findContestants(String teamName)  {
        try {
            return server.findContestants(teamName);
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerContestant(String cname, String tname, ArrayList<Race> races) {
        try {
            server.registerContestant(cname, tname, races);
        } catch (ContestException e) {
            throw new RuntimeException(e);
        }
    }
}