package com.ubb.mpp.persistence.mapper;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.model.Team;
import com.ubb.mpp.persistence.Repository;
import com.ubb.mpp.persistence.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marius Adam
 */
@Service
public class ContestantMapper implements Mapper<Contestant> {
    private Repository<Integer, Team> teamRepository;
    private Repository<Integer, EngineCapacity> engineCapacityRepository;

    @Autowired
    public ContestantMapper(Repository<Integer, Team> teamRepository, Repository<Integer, EngineCapacity> engineCapacityRepository) {
        this.teamRepository = teamRepository;
        this.engineCapacityRepository = engineCapacityRepository;
    }

    @Override
    public Map<String, String> toMap(Contestant obj) {
        Map<String, String> map = new HashMap<>();

        if (obj.getId() != null) {
            map.put("id", obj.getId().toString());
        }
        map.put("name", obj.getName());
        map.put("team_id", obj.getTeam().getId().toString());
        map.put("engine_capacity_id", obj.getEngineCapacity().getId().toString());

        return map;
    }

    @Override
    public Contestant createObject(ResultSet rs) throws RepositoryException {
        Contestant c = new Contestant();
        try {
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setTeam(teamRepository.findById(rs.getInt("team_id")));
            c.setEngineCapacity(
                    engineCapacityRepository.findById(rs.getInt("engine_capacity_id"))
            );
            return c;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
