package com.ubb.mpp.motorcyclingcontest.repository.mapper;

import com.ubb.mpp.motorcyclingcontest.domain.EngineCapacity;
import com.ubb.mpp.motorcyclingcontest.repository.RepositoryException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marius Adam
 */
@Service
public class EngineCapacityMapper implements Mapper<EngineCapacity> {
    @Override
    public Map<String, String> toMap(EngineCapacity obj) {
        Map<String, String> map = new HashMap<>();

        if (obj.getId() != null) {
            map.put("id", obj.getId().toString());
        }
        map.put("capacity", obj.getCapacity().toString());
        map.put("unit_of_measurement", obj.getUnitOfMeasurement().toString());
        return map;
    }

    @Override
    public EngineCapacity createObject(ResultSet rs) throws RepositoryException {
        EngineCapacity obj = new EngineCapacity();
        try {
            obj.setId(rs.getInt("id"));
            obj.setCapacity(rs.getDouble("capacity"));
            obj.setUnitOfMeasurement(
                    EngineCapacity.UM.fromName(
                            rs.getString("unit_of_measurement")
                    )
            );

            return obj;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }
}
