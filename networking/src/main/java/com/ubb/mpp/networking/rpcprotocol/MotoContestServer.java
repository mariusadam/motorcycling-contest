package com.ubb.mpp.networking.rpcprotocol;

import com.ubb.mpp.model.User;
import com.ubb.mpp.networking.dto.DTOUtils;
import com.ubb.mpp.networking.dto.UserDTO;
import com.ubb.mpp.services.ClientInterface;
import com.ubb.mpp.services.ServerInterface;
import com.ubb.mpp.services.crud.ServerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Marius Adam.
 */
public class MotoContestServer implements ServerInterface {
    private String host;
    private int port;

    private ClientInterface client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> responsesQueue;
    private volatile boolean finished;

    public MotoContestServer(String host, int port) {
        this.host = host;
        this.port = port;
        responsesQueue = new LinkedBlockingQueue<Response>();
    }

    public void login(User user, ClientInterface client) throws ServerException {
        initializeConnection();
        UserDTO udto = DTOUtils.getDTO(user);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(udto).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            this.client = client;
            return;
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new ServerException(err);
        }
    }

    public void logout(User user, ClientInterface client) throws ServerException {
        UserDTO udto = DTOUtils.getDTO(user);
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(udto).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ServerException(err);
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws ServerException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ServerException("Error sending object " + e);
        }

    }

    private Response readResponse() throws ServerException {
        try {
            return responsesQueue.take();
        } catch (InterruptedException e) {
            throw new ServerException(e);
        }
    }

    private void initializeConnection() throws ServerException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(Response response) {
        //TODO

    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.UPDATE;
        //TODO
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {

                        try {
                            responsesQueue.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
