package com.ubb.mpp.motorcyclyingcontest.repository.mapper;

import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marius Adam
 */
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
        map.put("is_active", !obj.getIsActive() ? "0" : "1");
        map.put("logged_in", !obj.getLoggedIn() ? "0" : "1");
        if (obj.getLastLogin() != null) {
            map.put("last_login", obj.getLastLogin().toString());
        }

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
            u.setIsActive(rs.getBoolean("is_active"));
            u.setLoggedIn(rs.getBoolean("logged_in"));
            u.setLastLogin(rs.getDate("last_login"));
            
            return u;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
