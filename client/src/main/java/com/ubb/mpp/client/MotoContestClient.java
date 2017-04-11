package com.ubb.mpp.client;

import motocontest.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.List;

/**
 * @author Marius Adam
 */
public class MotoContestClient extends WebServiceGatewaySupport {
    private final static String NS = "http://localhost:8080/ws/moto-contest.wsdl";

    private static final Logger log = LoggerFactory.getLogger(MotoContestClient.class);

    public LoginResponse loginResponse(String email, String password) {

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        log.info("Requesting quote for " + email + " " + password);

        LoginResponse response = (LoginResponse) getWebServiceTemplate()
                .marshalSendAndReceive(NS, request);
//        new SoapActionCallback()
        return response;
    }

    public GetEngineCapacitiesResponse getAllCapacitiesResponse() {
        GetEngineCapacitiesRequest r = new GetEngineCapacitiesRequest();
        return (GetEngineCapacitiesResponse) getWebServiceTemplate().marshalSendAndReceive(NS, r);
    }

    public GetRacesResponse getAllRacesResponse() {
        GetRacesRequest r = new GetRacesRequest();
        return (GetRacesResponse) getWebServiceTemplate().marshalSendAndReceive(NS, r);
    }

    public GetContestantsResponse getContestantsResponse() {
        GetContestantsRequest r = new GetContestantsRequest();
        return (GetContestantsResponse) getWebServiceTemplate().marshalSendAndReceive(NS, r);
    }

    public SuggestTeamNamesResponse suggestTeamNamesResponse(String text) {
        SuggestTeamNamesRequest r = new SuggestTeamNamesRequest();
        r.setText(text);
        return (SuggestTeamNamesResponse) getWebServiceTemplate().marshalSendAndReceive(NS, r);
    }

    public FindContestantsByTeamNameResponse findByTeamNameResponse(String text) {
        FindContestantsByTeamNameRequest r = new FindContestantsByTeamNameRequest();
        r.setTeamName(text);
        return (FindContestantsByTeamNameResponse) getWebServiceTemplate().marshalSendAndReceive(NS, r);
    }

    public SuggestRaceNamesResponse suggestRaceNamesResponse(String text) {
        SuggestRaceNamesRequest r = new SuggestRaceNamesRequest();
        r.setText(text);
        return (SuggestRaceNamesResponse) getWebServiceTemplate().marshalSendAndReceive(NS, r);
    }

    public FindRacesByNameAndCapacitiesResponse findRacesByNameAndCapacitiesResponse(String raceName, List<EngineCapacity> capacities) {
        FindRacesByNameAndCapacitiesRequest r = new FindRacesByNameAndCapacitiesRequest();
        r.setRaceName(raceName);
        r.getCapacities().addAll(capacities);
        return (FindRacesByNameAndCapacitiesResponse) getWebServiceTemplate().marshalSendAndReceive(NS, r);
    }
}