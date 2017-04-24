package com.ubb.mpp.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Marius Adam
 */
public interface ObserverService extends Remote {
    void contestantRegistered() throws RemoteException;
    String getObserverId();
}
