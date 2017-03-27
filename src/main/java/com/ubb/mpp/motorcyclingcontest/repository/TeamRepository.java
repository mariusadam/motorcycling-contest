package com.ubb.mpp.motorcyclingcontest.repository;

import com.ubb.mpp.motorcyclingcontest.domain.Team;
import com.ubb.mpp.motorcyclingcontest.repository.mapper.Mapper;
import com.ubb.mpp.motorcyclingcontest.service.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class TeamRepository extends DbRepository<Integer, Team> {
    @Autowired
    public TeamRepository(Adapter adapter, Mapper<Team> mapper,
                          @Value("${table.teams")
                          String tableName) {
        super(adapter, mapper, tableName);
    }
}
