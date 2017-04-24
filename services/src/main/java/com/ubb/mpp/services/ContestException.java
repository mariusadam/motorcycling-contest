package com.ubb.mpp.services;

/**
 * @author Marius Adam
 */
public class ContestException extends Exception {
    public ContestException() {
        super();
    }

    public ContestException(String message) {
        super(message);
    }

    public ContestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContestException(Throwable cause) {
        super(cause);
    }

    protected ContestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
