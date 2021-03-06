package com.ubb.mpp.persistence;

import com.ubb.mpp.model.Team;
import com.ubb.mpp.persistence.mapper.Mapper;
import com.ubb.mpp.persistence.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class TeamRepository extends DbRepository<Integer, Team> {
    @Autowired
    public TeamRepository(Adapter adapter, Mapper<Team> mapper,
                          @Value("${table.teams}")
                          String tableName) {
        super(adapter, mapper, tableName);
    }

    public Collection<String> suggestNames(String userText) {
        return suggest("name", userText);
    }
}
