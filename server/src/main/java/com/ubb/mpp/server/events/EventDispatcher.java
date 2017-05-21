package com.ubb.mpp.server.events;

/**
 * @author Marius Adam
 */
public interface EventDispatcher {
    void dispatch(Event event);
}
