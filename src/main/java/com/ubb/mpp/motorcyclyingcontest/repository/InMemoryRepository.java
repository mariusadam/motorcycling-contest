package com.ubb.mpp.motorcyclyingcontest.repository;

import com.ubb.mpp.motorcyclyingcontest.domain.HasId;

import java.util.*;

/**
 * @author Marius Adam
 */
public class InMemoryRepository<Id, T extends HasId<Id>> implements Repository<Id, T> {
    private Map<Id, T> items;

    public InMemoryRepository() {
        items = new Hashtable<>();
    }

    /**
     * Inserts a new entity into the repository
     *
     * @param obj The object to be inserted
     * @throws RepositoryException If there is already an entity with the same id
     */
    @Override
    public void insert(T obj) throws RepositoryException {
        if (items.containsKey(obj.getId())) {
            throw new RepositoryException();
        }
        items.put(obj.getId(),obj);
    }

    /**
     * Removes the entity from the repository
     *
     * @param id The id of the entity to be deleted
     * @return Entity The deleted entity
     */
    @Override
    public T delete(Id id) {
        T deleted = findById(id);
        items.remove(id);

        return deleted;
    }

    /**
     * Updates the given entity by identifying the entity from
     * within the repository by id
     *
     * @param entity The entity to be updated
     */
    @Override
    public void update(T entity) {
        T obj = findById(entity.getId());
        items.put(obj.getId(), entity);
    }

    /**
     * Searches for an entity with a given id
     *
     * @param id The id of the searched entity
     * @return Entity The searched entity
     * @throws NoSuchElementException If the searched entity is not found
     */
    @Override
    public T findById(Id id) {
        T obj = items.get(id);
        if (obj != null) {
            return obj;
        }
        throw new NoSuchElementException("Could not find the entity with id " + id);
    }

    @Override
    public Collection<T> findBy(Map<String, String> criteria) throws RepositoryException {
        return null;
    }

    @Override
    public Collection<T> findBy(String property, Object value) throws RepositoryException {
        return null;
    }

    @Override
    public T findOneBy(String property, Object value) throws RepositoryException {
        return null;
    }

    @Override
    public void addCollection(Collection<T> collection) throws RepositoryException {
        for (T obj : collection) {
            insert(obj);
        }
    }

    @Override
    public int size() {
        return items.size();
    }

    /**
     *
     * @return {@link Collection} The object containing all the entities
     */
    @Override
    public Collection<T> getAll() {
        return items.values();
    }
}