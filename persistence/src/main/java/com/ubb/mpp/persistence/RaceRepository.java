package com.ubb.mpp.persistence;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.model.Race;
import com.ubb.mpp.persistence.mapper.Mapper;
import com.ubb.mpp.persistence.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class RaceRepository extends DbRepository<Integer, Race> {
    @Autowired
    public RaceRepository(Adapter adapter, Mapper<Race> mapper,
                          @Value("${table.races}") String tableName) {
        super(adapter, mapper, tableName);
    }

    public List<Race> findByNameAndCapacities(String name, Collection<EngineCapacity> capacities) throws RepositoryException {
        String query = String.format("SELECT r.* FROM `%s` r", tableName);
        Collection<String> where = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        name = name.trim();
        if (!name.isEmpty()) {
            where.add("r.name LIKE ?");
            params.add("%" + name.toLowerCase() + "%");
        }
        Collection<String> ids = capacities.stream().map(e -> e.getId().toString()).collect(Collectors.toList());
        if (!capacities.isEmpty()) {
            where.add(String.format("r.engine_capacity_id IN (%s)", String.join(", ", ids)));
        }

        if (!where.isEmpty()) {
            query += String.format(" WHERE %s", String.join(" AND ", where));
        }

        try {
            PreparedStatement stmt = adapter.getConnection().prepareStatement(query);
            for(int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }

            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            List<Race> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapper.createObject(rs));
            }
            rs.close();

            return result;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    public List<String> suggestNames(String userText) throws RepositoryException {
        return suggest("name", userText);
    }

    public void registerContestant(Contestant contestant, Race race) throws RepositoryException {
        String insertQuery = "INSERT INTO `contestant_race` (contestant_id, race_id) VALUES (?, ?)";
        try {
            PreparedStatement stmt = adapter.getConnection().prepareStatement(insertQuery);
            stmt.setString(1, contestant.getId().toString());
            stmt.setString(2, race.getId().toString());
            stmt.execute();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    public int countParticipants(Race race) {
        String insertQuery = "SELECT COUNT(*) AS total FROM `contestant_race` cr WHERE cr.race_id = ?";
        try {
            PreparedStatement stmt = adapter.getConnection().prepareStatement(insertQuery);
            stmt.setInt(1, race.getId());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return rs.getInt("total");
            }

            return 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}

