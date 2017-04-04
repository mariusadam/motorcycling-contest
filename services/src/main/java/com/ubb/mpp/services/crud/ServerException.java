package com.ubb.mpp.services.crud;

/**
 * @author Marius Adam
 */
public class ServerException extends Exception {
    public ServerException() {
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }
}
