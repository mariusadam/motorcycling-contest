package com.ubb.mpp.model;

import java.io.Serializable;

/**
 *
 */
public abstract class Entity<Id extends Serializable> implements HasId<Id> {
    private Id id;

    public Entity() {
    }

    /**
     *
     * @param id The unique id identifying an entity
     */
    public Entity(Id id) {
        this.id = id;
    }

    /**
     * @return Id
     */
    public Id getId() {
        return id;
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Entity && id != null && id.equals(((Entity) obj).id);
    }
}
