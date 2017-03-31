package com.ubb.mpp.motorcyclingcontest.domain;

import java.util.Date;

/**
 * @author Marius Adam
 */
public class Race extends Entity<Integer> {
    private String name;
    private Date startTime;
    private EngineCapacity engineCapacity;

    public EngineCapacity getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(EngineCapacity engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
