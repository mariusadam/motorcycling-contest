package com.ubb.mpp.persistence;

import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.persistence.mapper.Mapper;
import com.ubb.mpp.persistence.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marius Adam
 */
@org.springframework.stereotype.Repository
public class ContestantRepository extends DbRepository<Integer, Contestant> {
    @Autowired
    public ContestantRepository(Adapter adapter, Mapper<Contestant> mapper,
                                @Value("${table.contestant}") String tableName) {
        super(adapter, mapper, tableName);
    }

    public List<Contestant> findByTeamName(String text) {
        try {
            String query = String.format(
                    "SELECT r.* from %s r " +
                            "INNER JOIN team t on t.id = r.team_id " +
                            "WHERE t.name like ?",
                    tableName
            );
            PreparedStatement stmt = adapter.getConnection().prepareStatement(query);
            stmt.setString(1, "%" + text + "%");

            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            List<Contestant> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapper.createObject(rs));
            }
            rs.close();

            return result;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
