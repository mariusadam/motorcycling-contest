package com.ubb.mpp.motorcyclingcontest.domain;

/**
 * @author Marius Adam
 */
public class Contestant extends Entity<Integer> {
    private String nume;
    private EngineCapacity engineCapacity;
    private Team team;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public EngineCapacity getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(EngineCapacity engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
