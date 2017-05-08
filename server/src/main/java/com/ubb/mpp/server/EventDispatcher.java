package com.ubb.mpp.server;

import com.ubb.mpp.motocontest.generated.Event;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author Marius Adam
 */
@Component
public class EventDispatcher {
    private static final Logger logger = Logger.getLogger(EventDispatcher.class.getName());

    Map<Event.Name, List<StreamObserver<Event>>> listeners;

    public EventDispatcher() {
        this.listeners = Collections.synchronizedMap(new HashMap<>());
    }

    public void addListener(Event.Name eventName, StreamObserver<Event> listener) {
        List<StreamObserver<Event>> events = listeners.computeIfAbsent(eventName, k -> Collections.synchronizedList(new ArrayList<>()));

        events.add(listener);
    }

    public synchronized void dispatch(Event event) {
        List<StreamObserver<Event>> subscribers = listeners.get(event.getName());
        List<StreamObserver<Event>> toRemove = new ArrayList<>();
        if (subscribers != null) {
            for (StreamObserver<Event> eventStreamObserver : subscribers) {
                try {
                    eventStreamObserver.onNext(event);
                } catch (Throwable ex) {
                    logger.warning(ex.getMessage());
                    toRemove.add(eventStreamObserver);
                }
            }
            subscribers.removeAll(toRemove);
        }
    }

    public synchronized void dispatch(Event.Name eventName) {
        dispatch(Event
                .newBuilder()
                .setName(eventName)
                .build()
        );
    }
}
