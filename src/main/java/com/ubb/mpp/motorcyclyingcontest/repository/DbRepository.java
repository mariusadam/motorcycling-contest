package com.ubb.mpp.motorcyclyingcontest.repository;

import com.ubb.mpp.motorcyclyingcontest.domain.HasId;
import com.ubb.mpp.motorcyclyingcontest.repository.mapper.Mapper;
import com.ubb.mpp.motorcyclyingcontest.service.adapter.Adapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Marius Adam
 */
public class DbRepository<Id, T extends HasId<Id>> implements Repository<Id, T> {
    private Adapter adapter;
    private Mapper<T> mapper;
    private String tableName;

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
        T obj = findBy("id", id).iterator().next();
        if (obj == null || obj.getId() == null) {
            throw new NoSuchElementException("Could not find the entity with id " + id);
        }

        return obj;
    }

    @Override
    public Collection<T> findBy(String property, Object value) throws RepositoryException {
        Map<String, String> filter = new HashMap<>();
        filter.put(property, value.toString());
        return findBy(filter);
    }

    @Override
    public T findOneBy(String property, Object value) throws RepositoryException {
        return findBy(property, value).iterator().next();
    }

    @Override
    public Collection<T> findBy(Map<String, String> criteria) throws RepositoryException {
        try {
            PreparedStatement stmt = adapter
                    .getSelectStatement(tableName, criteria);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            Collection<T> result = new ArrayList<>();
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
    public Collection<T> getAll() throws RepositoryException {
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
}