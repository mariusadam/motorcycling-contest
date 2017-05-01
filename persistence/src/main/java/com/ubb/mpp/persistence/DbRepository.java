package com.ubb.mpp.persistence;

import com.ubb.mpp.model.HasId;
import com.ubb.mpp.persistence.adapter.Adapter;
import com.ubb.mpp.persistence.mapper.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Marius Adam
 */
public class DbRepository<Id, T extends HasId<Id>> implements Repository<Id, T> {
    protected Adapter adapter;
    protected Mapper<T> mapper;
    protected String tableName;

    public DbRepository(Adapter adapter, Mapper<T> mapper, String tableName) {
        this.adapter = adapter;
        this.mapper = mapper;
        this.tableName = tableName;
    }

    @Override
    public void insert(T obj) throws RepositoryException {
        try {
            adapter
                    .getInsertStatement(tableName, mapper.toMap(obj))
                    .execute();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public T delete(Id id) throws RepositoryException {
        T obj = findById(id);

        try {
            adapter.getDeleteStatement(tableName, id);
            return obj;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(T entity) throws RepositoryException {
        Map<String, String> props = mapper.toMap(entity);
        try {
            adapter.getUpdateStatement(tableName, props);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public T findById(Id id) throws RepositoryException {
        T obj = findOneBy("id", id);
        if (obj == null || obj.getId() == null) {
            throw new NoSuchElementException("Could not find the entity with id " + id);
        }

        return obj;
    }

    @Override
    public List<T> findBy(String property, Object value) throws RepositoryException {
        Map<String, String> filter = new HashMap<>();
        filter.put(property, value.toString());
        return findBy(filter);
    }

    @Override
    public T findOneBy(String property, Object value) throws RepositoryException {
        Collection<T> result = findBy(property, value);
        if (result.isEmpty()) {
            throw new RepositoryException(String.format(
                    "Could not find an item with \"%s\" equal to \"%s\"",
                    property,
                    value)
            );
        }

        return result.iterator().next();
    }

    @Override
    public List<T> findBy(Map<String, String> criteria) throws RepositoryException {
        try {
            PreparedStatement stmt = adapter
                    .getSelectStatement(tableName, criteria);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapper.createObject(rs));
            }
            rs.close();

            return result;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<T> getAll() throws RepositoryException {
        return findBy(null);
    }

    @Override
    public void addCollection(Collection<T> collection) throws RepositoryException {
        for (T o: collection) {
            insert(o);
        }
    }

    @Override
    public int size() throws RepositoryException {
        //obviously this will be changed
        return getAll().size();
    }

    public List<String> suggest(String column, Object value) throws RepositoryException {
        try {
            String query = String.format(
                    "SELECT r.%s from %s r WHERE r.%s like ? limit 10",
                    column,
                    tableName,
                    column
            );
            PreparedStatement stmt = adapter.getConnection().prepareStatement(query);
            stmt.setString(1, "%" + value + "%");

            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            List<String> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getString(column));
            }
            rs.close();

            return result;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}