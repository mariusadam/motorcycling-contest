package com.ubb.mpp.server;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.ubb.mpp.persistence.RepositoryException;
import com.ubb.mpp.services.crud.*;
import motocontest.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marius Adam
 */
@Endpoint
public class MotoContestEndpoint {
    private static final String NAMESPACE_URI = "http://moto-contest.org/api";

    private UserService userService;
    private RaceService raceService;
    private EngineCapacityService ecService;
    private ContestantService contestantService;
    private TeamService teamService;

    @Autowired
    public MotoContestEndpoint(UserService userService, RaceService raceService, EngineCapacityService ecService, ContestantService contestantService, TeamService teamService) {
        this.userService = userService;
        this.raceService = raceService;
        this.ecService = ecService;
        this.contestantService = contestantService;
        this.teamService = teamService;
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEngineCapacitiesRequest")
    @ResponsePayload
    public GetEngineCapacitiesResponse getEngineCapacities(@RequestPayload GetEngineCapacitiesRequest request) {
        GetEngineCapacitiesResponse response = new GetEngineCapacitiesResponse();
        for (com.ubb.mpp.model.EngineCapacity em: ecService.getAll()) {
            response.getEngineCapacities().add(fromModel(em));
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRacesRequest")
    @ResponsePayload
    public GetRacesResponse getRaces(@RequestPayload GetRacesRequest request) {
        GetRacesResponse response = new GetRacesResponse();
        for (com.ubb.mpp.model.Race em: raceService.getAll()) {
            Race ew = new Race();
            ew.setEngineCapacity(fromModel(em.getEngineCapacity()));
            ew.setName(em.getName());
            ew.setStartTime(new XMLGregorianCalendarImpl());
            response.getRaces().add(ew);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getContestantsRequest")
    @ResponsePayload
    public GetContestantsResponse getContestants(@RequestPayload GetContestantsRequest request) {
        GetContestantsResponse response = new GetContestantsResponse();
        for (com.ubb.mpp.model.Contestant em: contestantService.getAll()) {
            response.getContestants().add(fromModel(em));
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "suggestTeamNamesRequest")
    @ResponsePayload
    public SuggestTeamNamesResponse suggestTeamNames(@RequestPayload SuggestTeamNamesRequest request) {
        SuggestTeamNamesResponse response = new SuggestTeamNamesResponse();
        response.getSuggestions().addAll(teamService.suggestNames(request.getText()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findContestantsByTeamNameRequest")
    @ResponsePayload
    public FindContestantsByTeamNameResponse suggestTeamNames(
            @RequestPayload FindContestantsByTeamNameRequest request) {
        FindContestantsByTeamNameResponse response = new FindContestantsByTeamNameResponse();
        for (com.ubb.mpp.model.Contestant c : contestantService.findByTeamName(request.getTeamName())) {
            response.getContestants().add(fromModel(c));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "suggestRaceNamesRequest")
    @ResponsePayload
    public SuggestRaceNamesResponse suggestTeamNames(
            @RequestPayload SuggestRaceNamesRequest request) {
        SuggestRaceNamesResponse response = new SuggestRaceNamesResponse();
        response.getSuggestions().addAll(raceService.suggestNames(request.getText()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findRacesByNameAndCapacitiesRequest")
    @ResponsePayload
    public FindRacesByNameAndCapacitiesResponse suggestTeamNames(
            @RequestPayload FindRacesByNameAndCapacitiesRequest request) {
        FindRacesByNameAndCapacitiesResponse response = new FindRacesByNameAndCapacitiesResponse();
        for (com.ubb.mpp.model.Race race : raceService.findByNameAndCapacities(
                request.getRaceName(), fromWsdl(request.getCapacities())
        )) {
            response.getRaces().add(fromModel(race));
        }
        return response;
    }

    private Race fromModel(com.ubb.mpp.model.Race race) {
        Race ew = new Race();
        ew.setEngineCapacity(fromModel(race.getEngineCapacity()));
        ew.setId(new BigInteger(race.getId().toString()));
        ew.setName(race.getName());

        return ew;
    }

    protected List<com.ubb.mpp.model.EngineCapacity> fromWsdl(List<EngineCapacity> items) {
        List<com.ubb.mpp.model.EngineCapacity> result = new ArrayList<>();
        for (EngineCapacity e : items) {
            result.add(fromWsdl(e));
        }

        return result;
    }

    private com.ubb.mpp.model.EngineCapacity fromWsdl(EngineCapacity e) {
        com.ubb.mpp.model.EngineCapacity em = new com.ubb.mpp.model.EngineCapacity();
        em.setId(e.getId().intValue());
        em.setCapacity(e.getCapacity());
        em.setUnitOfMeasurement(com.ubb.mpp.model.EngineCapacity.UM.CC);

        return em;
    }

    protected EngineCapacity fromModel(com.ubb.mpp.model.EngineCapacity em) {
        EngineCapacity ew = new EngineCapacity();
        ew.setCapacity(em.getCapacity());
        ew.setUnitOfMeasurement(Um.CC);
        return ew;
    }

    protected Team fromModel(com.ubb.mpp.model.Team em) {
        Team ed = new Team();
        ed.setId(new BigInteger(em.getId().toString()));
        ed.setName(em.getName());
        return ed;
    }

    protected Contestant fromModel(com.ubb.mpp.model.Contestant em) {
        Contestant ed = new Contestant();
        ed.setId(new BigInteger(em.getId().toString()));
        ed.setName(em.getName());
        ed.setTeam(fromModel(em.getTeam()));
        return ed;
    }
}