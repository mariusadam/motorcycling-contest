package com.ubb.mpp.model;

import java.io.Serializable;

/**
 * @author Marius Adam
 */
public interface HasId<Id extends Serializable> extends Serializable{
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