package com.ubb.mpp.motorcyclyingcontest.repository.mapper;

import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marius on 07.03.2017.
 */
public class UserMapper implements Mapper<Integer, User> {
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
            u
                    .setFirstName(rs.getString("first_name"))
                    .setLastName(rs.getString("last_name"))
                    .setEmail(rs.getString("email"))
                    .setPassword(rs.getString("password"))
                    .setSalt(rs.getString("salt"))
                    .setIsActive(rs.getBoolean("is_active"))
                    .setLoggedIn(rs.getBoolean("logged_in"))
                    .setLastLogin(rs.getDate("last_login"));

            return u;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
