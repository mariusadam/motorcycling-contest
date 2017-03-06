package com.ubb.mpp.motorcyclyingcontest.domain;

/**
 *
 */
public abstract class Entity<Id> implements HasId<Id> {
    protected Id id;

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
        return obj instanceof Entity && this.id.equals(((Entity) obj).id);
    }
}
