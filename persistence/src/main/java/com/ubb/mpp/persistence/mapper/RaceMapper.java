package com.ubb.mpp.persistence.mapper;

import com.ubb.mpp.model.Race;
import com.ubb.mpp.persistence.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marius Adam
 */
@Service
public class RaceMapper implements Mapper<Race> {

    @Autowired
    public RaceMapper() {
    }

    @Override
    public Map<String, String> toMap(Race obj) {
        Map<String, String> map = new HashMap<>();

        if (obj.getId() != null) {
            map.put("id", obj.getId().toString());
        }
        map.put("name", obj.getName());
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(obj.getStartTime());

        map.put("start_time", formattedDate);
        return map;
    }

    @Override
    public Race createObject(ResultSet rs) throws RepositoryException {
        Race obj = new Race();
        try {
            obj.setId(rs.getInt("id"));
            obj.setName(rs.getString("name"));
            obj.setStartTime(rs.getTimestamp("start_time"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return obj;
    }
}
