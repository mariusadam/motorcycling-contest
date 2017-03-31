package com.ubb.mpp.motorcyclingcontest.domain;

/**
 * @author Marius Adam
 */
public class Team extends Entity<Integer> {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
