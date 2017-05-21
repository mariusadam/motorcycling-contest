package com.ubb.mpp.server.events;

import com.ubb.mpp.server.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author Marius Adam
 */
@Component
public class GrpcEventDispatcher implements EventDispatcher{
    private static final Logger logger = Logger.getLogger(GrpcEventDispatcher.class.getName());
    private Converter converter;
//    Map<Event.Name, List<StreamObserver<Event>>> listeners;

    @Autowired
    public GrpcEventDispatcher(Converter converter) {
        this.converter = converter;
//        this.listeners = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void dispatch(com.ubb.mpp.server.events.Event event) {
        // TODO
    }
}
