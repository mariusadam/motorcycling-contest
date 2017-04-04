package com.ubb.mpp.services.crud;

/**
 * @author Marius Adam
 */
public class ClientException extends Exception {
    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }
}
