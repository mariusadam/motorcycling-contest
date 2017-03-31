package com.ubb.mpp.motorcyclingcontest.repository;

import com.ubb.mpp.motorcyclingcontest.domain.EngineCapacity;
import com.ubb.mpp.motorcyclingcontest.repository.mapper.Mapper;
import com.ubb.mpp.motorcyclingcontest.service.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class EngineCapacityRepository extends DbRepository<Integer, EngineCapacity> {
    @Autowired
    public EngineCapacityRepository(Adapter adapter, Mapper<EngineCapacity> mapper,
                                    @Value("${table.engine_capacity}") String tableName) {
        super(adapter, mapper, tableName);
    }
}
