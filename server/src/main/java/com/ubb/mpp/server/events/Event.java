package com.ubb.mpp.server.events;

/**
 * @author Marius Adam
 */
public enum Event {
    ContestantRegistered("motocontest.events.contestant.registered"),
    TeamAdded("motocontest.events.team.added");

    private String name;

    Event(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}
