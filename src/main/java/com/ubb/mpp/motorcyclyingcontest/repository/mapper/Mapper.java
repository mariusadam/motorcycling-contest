package com.ubb.mpp.motorcyclyingcontest.repository.mapper;

import com.ubb.mpp.motorcyclyingcontest.repository.RepositoryException;

import java.sql.ResultSet;
import java.util.Map;

/**
 * Created by marius on 07.03.2017.
 */
public interface Mapper<Id, T> {
    public Map<String, String> toMap(T obj);

    public T createObject(ResultSet rs) throws RepositoryException;
}
