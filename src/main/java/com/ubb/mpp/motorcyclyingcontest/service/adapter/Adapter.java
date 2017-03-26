package com.ubb.mpp.motorcyclyingcontest.service.adapter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Marius Adam
 */
public interface Adapter {
    PreparedStatement getInsertStatement(String tableName, Map<String, String> properties) throws SQLException;
    PreparedStatement getDeleteStatement(String tableName, Object idValue) throws SQLException;
    PreparedStatement getUpdateStatement(String tableName, Map<String, String> properties) throws SQLException;
    PreparedStatement getSelectStatement(String tableName, Map<String, String> criteria) throws SQLException;
}
