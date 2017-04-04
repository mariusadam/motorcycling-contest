package com.ubb.mpp.model;

/**
 * @author Marius Adam
 */
public class EngineCapacity extends Entity<Integer> {
    private UM unitOfMeasurement;
    private Double capacity;

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public UM getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UM unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void toCc() {
        switch (unitOfMeasurement) {
            case MC:
                capacity = capacity * 1000;
                break;
            case CC:
                break;
        }

        unitOfMeasurement = UM.CC;
    }

    @Override
    public String toString() {
        return capacity.toString() + unitOfMeasurement.toString();
    }

    public enum UM {
        MC("mc"),
        CC("cc");

        private String name;

        UM(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        public static UM fromName(String name) {
            switch (name) {
                case "mc":
                    return MC;
                case "cc":
                    return CC;
                default:
                    return null;
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
