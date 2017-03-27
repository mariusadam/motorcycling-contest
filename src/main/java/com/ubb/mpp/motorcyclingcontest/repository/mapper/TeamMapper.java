package com.ubb.mpp.motorcyclingcontest.repository.mapper;

import com.ubb.mpp.motorcyclingcontest.domain.Team;
import com.ubb.mpp.motorcyclingcontest.repository.RepositoryException;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marius Adam
 */
@Component
public class TeamMapper implements Mapper<Team> {
    @Override
    public Map<String, String> toMap(Team obj) {
        Map<String, String> map = new HashMap<>();

        if (obj.getId() != null) {
            map.put("id", obj.getId().toString());
        }
        map.put("name", obj.getName());
        return map;
    }

    @Override
    public Team createObject(ResultSet rs) throws RepositoryException {
        Team t = new Team();
        try {
            t.setId(rs.getInt("id"));
            t.setName(rs.getString("name"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return t;
    }
}
