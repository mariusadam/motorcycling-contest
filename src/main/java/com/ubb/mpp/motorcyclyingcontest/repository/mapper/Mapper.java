package com.ubb.mpp.motorcyclyingcontest.repository.mapper;

import com.ubb.mpp.motorcyclyingcontest.repository.RepositoryException;

import java.sql.ResultSet;
import java.util.Map;

/**
 * @author Marius Adam
 */
public interface Mapper<T> {
    Map<String, String> toMap(T obj);
    T createObject(ResultSet rs) throws RepositoryException;
}
