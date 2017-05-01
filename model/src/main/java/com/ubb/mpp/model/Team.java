package com.ubb.mpp.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Marius Adam
 */
public class Team extends Entity<Integer> {
    @NotNull
    @Size(max = 10, min = 3)
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
