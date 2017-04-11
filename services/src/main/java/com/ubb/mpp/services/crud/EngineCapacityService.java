package com.ubb.mpp.services.crud;

import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.persistence.EngineCapacityRepository;
import com.ubb.mpp.persistence.Repository;
import com.ubb.mpp.services.validator.ValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marius Adam
 */
@Component
public class EngineCapacityService extends BaseCrudService<Integer, EngineCapacity> {
    private EngineCapacityRepository engineCapacityRepository;

    @Autowired
    public EngineCapacityService(ValidatorInterface validator, EngineCapacityRepository engineCapacityRepository) {
        super(validator);
        this.engineCapacityRepository = engineCapacityRepository;
    }

    @Override
    protected Repository<Integer, EngineCapacity> getRepository() {
        return engineCapacityRepository;
    }
}
