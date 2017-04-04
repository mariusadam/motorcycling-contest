package com.ubb.mpp.model;

/**
 * @author Marius Adam
 */
public class Contestant extends Entity<Integer> {
    private String name;
    private Team team;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
