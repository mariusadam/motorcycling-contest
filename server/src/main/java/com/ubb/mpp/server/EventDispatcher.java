package com.ubb.mpp.server;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author Marius Adam
 */
public class EventDispatcher {
    private static final Logger logger = Logger.getLogger(EventDispatcher.class.getName());

    Map<Event.Name, List<StreamObserver<Event>>> listeners;

    public EventDispatcher() {
        this.listeners = Collections.synchronizedMap(new HashMap<>());
    }

    public void addListener(Event.Name eventName, StreamObserver<Event> listener) {
        List<StreamObserver<Event>> events = listeners.get(eventName);
        if (events == null) {
            events = Collections.synchronizedList(new ArrayList<>());
            listeners.put(eventName, events);
        }

        events.add(listener);
    }

    public synchronized void dispatch(Event event) {
        List<StreamObserver<Event>> subscribers = listeners.get(event.getName());
        List<StreamObserver<Event>> toRemove = new ArrayList<>();
        if (subscribers != null) {
            for (StreamObserver<Event> eventStreamObserver : subscribers) {
                try {
                    eventStreamObserver.onNext(event);
                } catch (StatusRuntimeException ex) {
                    logger.warning(ex.getMessage());
                    toRemove.add(eventStreamObserver);
                }
            }
            subscribers.removeAll(toRemove);
        }
    }
}
