package com.ubb.mpp.server;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.model.Race;
import com.ubb.mpp.model.User;
import com.ubb.mpp.server.crud.*;
import com.ubb.mpp.services.ContestException;
import com.ubb.mpp.services.ContestServer;
import com.ubb.mpp.services.ObserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marius Adam
 */
@Component
public class ContestServerImpl implements ContestServer {
    private UserService userService;
    private RaceService raceService;
    private TeamService teamService;
    private EngineCapacityService engineCapacityService;
    private ContestantService contestantService;

    private Map<String, ObserverService> loggedClients;

    @Autowired
    public ContestServerImpl(UserService userService, RaceService raceService, TeamService teamService, EngineCapacityService engineCapacityService, ContestantService contestantService) {
        this.userService = userService;
        this.raceService = raceService;
        this.teamService = teamService;
        this.engineCapacityService = engineCapacityService;
        this.contestantService = contestantService;
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public User login(String email, String password, ObserverService client) throws ContestException {
        User u;
        try {
            u = userService.getUser(email, password);
        } catch (Exception e) {
            throw new ContestException(e);
        }

        if (u != null) {
            if (loggedClients.get(u.getEmail()) != null) {
                throw new ContestException("User already logged");
            }

            loggedClients.put(u.getEmail(), client);
            return u;
        }

        throw new ContestException("Login failed");
    }

    @Override
    public void logout(User user) throws ContestException {
        ObserverService client = loggedClients.get(user.getEmail());
        if (client == null) {
            throw new ContestException("User not logged in!");
        }
        loggedClients.remove(user.getEmail());
    }

    @Override
    public List<Contestant> getContestants() throws ContestException {
        return contestantService.getAll();
    }

    @Override
    public List<String> suggestTeamNames(String userText) throws ContestException {
        return teamService.suggestNames(userText);
    }

    @Override
    public List<Contestant> findContestants(String teamName) throws ContestException {
        return contestantService.findBy(teamName);
    }

    @Override
    public List<Race> findRaces(String name, List<EngineCapacity> capacities) throws ContestException {
        return raceService.findBy(name, capacities);
    }

    @Override
    public List<String> suggestRaceNames(String text) throws ContestException {
        return raceService.suggestNames(text);
    }

    @Override
    public List<Race> getRaces() throws ContestException {
        return raceService.getAll();
    }

    @Override
    public List<EngineCapacity> getEngineCapacities() throws ContestException {
        return engineCapacityService.getAll();
    }

    @Override
    public void registerContestant(String cname, String tname, ArrayList<Race> races) throws ContestException {
        Contestant contestant = contestantService.create(cname, tname);
        raceService.registerContestant(contestant, races);

        ExecutorService executor = Executors.newFixedThreadPool(loggedClients.size());

        for (ObserverService o : loggedClients.values()) {
            executor.execute(() -> {
                try {
                    o.contestantRegistered();
                } catch (RemoteException e) {
                    System.out.println("Error notifying ");
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}
