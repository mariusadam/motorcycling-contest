package com.ubb.mpp.motorcyclingcontest.repository;

import com.ubb.mpp.motorcyclingcontest.domain.EngineCapacity;
import com.ubb.mpp.motorcyclingcontest.domain.Race;
import com.ubb.mpp.motorcyclingcontest.repository.mapper.Mapper;
import com.ubb.mpp.motorcyclingcontest.service.adapter.Adapter;
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

    public List<Race> findByNameAndCapacities(String name, Collection<EngineCapacity> capacities) {
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

    public Collection<String> suggestNames(String userText) {
        return suggest("name", userText);
    }
}
