package com.ubb.mpp.services;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.model.Race;
import com.ubb.mpp.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marius Adam
 */
public interface ContestServer {
    User login(String email, String password, ObserverService observer) throws ContestException;
    void logout(User user) throws ContestException;

    List<Contestant> getContestants() throws ContestException;

    List<String> suggestTeamNames(String userText) throws ContestException;

    List<Contestant> findContestants(String teamName) throws ContestException;

    List<Race> findRaces(String name, List<EngineCapacity> capacities) throws ContestException;

    List<String> suggestRaceNames(String text) throws ContestException;

    List<Race> getRaces() throws ContestException;

    List<EngineCapacity> getEngineCapacities() throws ContestException;

    void registerContestant(String cname, String tname, ArrayList<Race> races) throws ContestException;
}
