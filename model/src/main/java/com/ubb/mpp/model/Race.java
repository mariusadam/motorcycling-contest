package com.ubb.mpp.model;

import java.util.Date;

/**
 * @author Marius Adam
 */
public class Race extends Entity<Integer> {
    private String name;
    private Date startTime;

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

    @Override
    public String toString() {
        return name;
    }
}
