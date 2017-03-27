package com.ubb.mpp.motorcyclingcontest.repository.query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marius Adam
 */
public class QueryBuilder {
    private Connection connection;

    private Map<String, ArrayList<String>> sqlParts;
    private String sql;
    private ArrayList<Object> params;
    private Type type;
    private State state;
    private Integer firstResult;
    private Integer maxResultrs;

    public QueryBuilder(Connection connection) {
        type = Type.SELECT;
        state = State.CLEAN;
        sql = null;
        params = new ArrayList<>();
        sqlParts = new HashMap<>();
        sqlParts.put("select", new ArrayList<>());
        sqlParts.put("from", new ArrayList<>());
        sqlParts.put("join", new ArrayList<>());
        sqlParts.put("set", new ArrayList<>());
        sqlParts.put("where", null);
        sqlParts.put("groupBy", new ArrayList<>());
        sqlParts.put("having", null);
        sqlParts.put("orderBy", new ArrayList<>());
        sqlParts.put("values", new ArrayList<>());
    }

    public Statement execute() {
        if (type.equals(Type.SELECT)) {
            return executeQuery(getSql(), params);
        } else {
            return executeUpdate(getSql(), params);
        }
    }

    private Statement executeUpdate(String sql, ArrayList<Object> params) {
        return null;
    }

    private Statement executeQuery(String sql, ArrayList<Object> params) {
        return null;
    }

    private String getSql() {
        if (sql != null && state == State.CLEAN) {
            return sql;
        }

        switch (type) {
            case SELECT:
                sql = getSqlForSelect();
                break;
            case INSERT:
                sql = getSqlForInsert();
                break;
            case UPDATE:
                sql = getSqlForUpdate();
                break;
            case DELETE:
                sql = getSqlForDelete();
                break;
            default:
                sql = getSqlForSelect();
        }

        state = State.DIRTY;
        return sql;
    }

    private String getSqlForSelect() {
        return null;
    }

    private String getSqlForUpdate() {
        return null;
    }

    private String getSqlForDelete() {
        return null;
    }

    private String getSqlForInsert() {
        return null;
    }

    private enum Type {
        SELECT, INSERT, UPDATE, DELETE
    }

    private enum State {
        DIRTY, CLEAN
    }
}
