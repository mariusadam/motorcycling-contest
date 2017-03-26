package com.ubb.mpp.motorcyclyingcontest.service.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Marius Adam
 */
@Service
public class MysqlAdapter implements Adapter {
    private Connection connection;

    @Autowired
    public MysqlAdapter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PreparedStatement getInsertStatement(String tableName, Map<String, String> properties) throws SQLException {
        Set<String> keys = properties.keySet();
        ArrayList<String> values = keys.stream().map(properties::get).collect(Collectors.toCollection(ArrayList::new));

        String cols = String.join(", ", properties.keySet());
        String vals = (new String(new char[properties.size() - 1]).replace("\0", "?, ")) + "?";
        String query = String.format("INSERT INTO `%s` (%s) VALUES (%s)", tableName, cols, vals);

        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < values.size(); i++) {
            stmt.setString(i + 1, values.get(i));
        }

        return stmt;
    }

    @Override
    public PreparedStatement getDeleteStatement(String tableName, Object idValue) throws SQLException {
        String query = String.format("DELETE FROM `%s` WHERE id=?", tableName);

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, idValue.toString());
        stmt.execute();
        return stmt;
    }

    @Override
    public PreparedStatement getUpdateStatement(String tableName, Map<String, String> properties) throws SQLException {
        String idValue = properties.get("id");
        properties.remove("id");

        ArrayList<String> set = new ArrayList<>();
        ArrayList<String> binds = new ArrayList<>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            binds.add(entry.getValue());
            set.add(entry.getKey() + "=?");
        }

        String setStr = String.join(", ", set);
        String where = "id=?";
        String query = String.format("UPDATE `%s` SET %s WHERE %s", tableName, setStr, where);

        PreparedStatement stmt = connection.prepareStatement(query);
        int i = 0;
        while (i < binds.size()) {
            stmt.setString(i + 1, binds.get(i));
            i++;
        }
        stmt.setString(i + 1, idValue);

        return stmt;
    }

    @Override
    public PreparedStatement getSelectStatement(String tableName, Map<String, String> criteria) throws SQLException {
        if (criteria == null) {
            criteria = new HashMap<>();
        }
        Set<String> keys = criteria.keySet();
        ArrayList<String> values = keys.stream().map(criteria::get).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<String> placeHolders = new ArrayList<>();
        keys.forEach(s -> placeHolders.add(s + "=?"));
        String where = String.join(" AND ", placeHolders);
        String query = String.format("SELECT t.* FROM `%s` t", tableName);
        if (!placeHolders.isEmpty()) {
            query += String.format(" WHERE %s", where);
        }

        PreparedStatement stmt = connection.prepareStatement(query);
        for(int i = 0; i < values.size(); i++) {
            stmt.setString(i + 1, values.get(i));
        }

        return stmt;
    }
}
