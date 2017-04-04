package com.ubb.mpp.persistence;

import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.persistence.mapper.Mapper;
import com.ubb.mpp.persistence.adapter.Adapter;
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
