package com.ubb.mpp.motorcyclyingcontest.repository;

import com.ubb.mpp.motorcyclyingcontest.domain.HasId;
import com.ubb.mpp.motorcyclyingcontest.repository.mapper.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by marius on 11/20/16.
 */
public class DbRepository<Id, T extends HasId<Id>> implements Repository<Id, T> {
    private Connection connection;
    private Mapper<Id, T> mapper;
    private String tableName;

    public DbRepository(Connection connection, Mapper<Id, T> mapper, String tableName) {
        this.connection = connection;
        this.mapper = mapper;
        this.tableName = tableName;
    }

    @Override
    public void insert(T obj) throws RepositoryException {
        Map<String, String> props = this.mapper.toMap(obj);
        Set<String> keys = props.keySet();
        ArrayList<String> values = keys.stream().map(props::get).collect(Collectors.toCollection(ArrayList::new));

        String cols = String.join(", ", props.keySet());
        String vals = (new String(new char[props.size() - 1]).replace("\0", "?, ")) + "?";
        String query = String.format("INSERT INTO `%s` (%s) VALUES (%s)", this.tableName, cols, vals);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            for(int i = 0; i < values.size(); i++) {
                stmt.setString(i + 1, values.get(i));
            }

            stmt.execute();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public T delete(Id id) throws RepositoryException {
        T obj = this.findById(id);

        String query = String.format("DELETE FROM `%s` WHERE id=?", this.tableName);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            stmt.execute();

            return obj;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(T entity) throws RepositoryException {
        Map<String, String> props = this.mapper.toMap(entity);
        props.remove("id");

        ArrayList<String> set = new ArrayList<>();
        ArrayList<String> binds = new ArrayList<>();
        for(Map.Entry<String, String> entry : props.entrySet()) {
            binds.add(entry.getValue());
            set.add(entry.getKey() + "=?");
        }

        String setStr = String.join(", ", set);
        String where = "id=?";
        String query = String.format("UPDATE `%s` SET %s WHERE %s", this.tableName, setStr ,where);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);

            int i = 0;
            while (i < binds.size()) {
                stmt.setString(i + 1, binds.get(i));
                i++;
            }
            stmt.setString(i + 1, entity.getId().toString());

            stmt.execute();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public T findById(Id id) throws RepositoryException {
        String query = String.format("SELECT t.* FROM `%s` t WHERE t.id=?", this.tableName);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            T obj = null;

            if (rs.next()) {
                obj =  this.mapper.createObject(rs);
            }
            rs.close();

            if (obj == null || obj.getId() == null) {
                throw new NoSuchElementException("Could not find the entity with id " + id);
            }
            return obj;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Collection<T> getAll() throws RepositoryException {
        String query = String.format("SELECT t.* FROM `%s` t", this.tableName);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            Collection<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(this.mapper.createObject(rs));
            }
            rs.close();

            return result;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void addCollection(Collection<T> collection) throws RepositoryException {
        for (T o: collection) {
            insert(o);
        }
    }

    @Override
    public int size() throws RepositoryException {
        String query = String.format("SELECT COUNT(*) as size FROM `%s`", this.tableName);

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            int size = 0;
            while (rs.next()) {
                size = rs.getInt("size");
            }
            rs.close();

            return size;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}