package com.ubb.mpp.client;

import motocontest.wsdl.LoginRequest;
import motocontest.wsdl.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * @author Marius Adam
 */
public class MotoContestClient extends WebServiceGatewaySupport {
    private final static String NS = "http://localhost:8080/ws/moto-contest.wsdl";

    private static final Logger log = LoggerFactory.getLogger(MotoContestClient.class);

    public LoginResponse login(String email, String password) {

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        log.info("Requesting quote for " + email + " " + password);

        LoginResponse response = (LoginResponse) getWebServiceTemplate()
                .marshalSendAndReceive(NS, request);
//        new SoapActionCallback()
        return response;
    }

}