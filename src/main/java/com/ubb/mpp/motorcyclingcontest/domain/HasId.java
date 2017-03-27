package com.ubb.mpp.motorcyclingcontest.domain;

/**
 * @author Marius Adam
 */
public interface HasId<Id> {
    /**
     *
     * @return Id
     */
    Id getId();

    /**
     *
     * @param id The id of the object
     */
    void setId(Id id);
}