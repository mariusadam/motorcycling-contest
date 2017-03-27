package com.ubb.mpp.motorcyclingcontest.repository.mapper;

import com.ubb.mpp.motorcyclingcontest.domain.User;
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
public class UserMapper implements Mapper<User> {
    @Override
    public Map<String, String> toMap(User obj) {
        Map<String, String> map = new HashMap<>();

        if (obj.getId() != null) {
            map.put("id", obj.getId().toString());
        }

        map.put("first_name", obj.getFirstName());
        map.put("last_name", obj.getLastName());
        map.put("email", obj.getEmail());
        map.put("password", obj.getPassword());
        map.put("salt", obj.getSalt());
        return map;
    }

    @Override
    public User createObject(ResultSet rs) throws RepositoryException {
        User u = new User();
        try {
            u.setId(rs.getInt("id"));
            u.setFirstName(rs.getString("first_name"));
            u.setLastName(rs.getString("last_name"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setSalt(rs.getString("salt"));
            return u;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
