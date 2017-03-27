package com.ubb.mpp.motorcyclingcontest.domain;

/**
 * @author Marius Adam
 */
public class EngineCapacity {
    private UM unitOfMeasurement;
    private Integer capacity;

    public EngineCapacity(Integer capacity, UM um) {
        this.capacity = capacity;
        this.unitOfMeasurement = um;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public UM getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UM unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EngineCapacity)) {
            return false;
        }

        EngineCapacity other = (EngineCapacity) obj;
        return other.unitOfMeasurement == unitOfMeasurement && other.capacity == capacity;
    }

    public enum UM {
        MC("mc");

        private String name;

        UM(String name) {
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
}
