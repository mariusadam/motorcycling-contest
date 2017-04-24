package com.ubb.mpp.server.crud;

import com.ubb.mpp.model.HasId;
import com.ubb.mpp.persistence.Repository;
import com.ubb.mpp.persistence.RepositoryException;
import com.ubb.mpp.server.validator.ValidatorInterface;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Marius Adam
 */
public abstract class BaseCrudService<Id, T extends HasId<Id>> {
    protected ValidatorInterface validator;

    public BaseCrudService(ValidatorInterface validator) {
        this.validator = validator;
    }

    public Collection<T> getAll() throws RepositoryException {
        return getRepository().getAll();
    }

    /**
     * @param id                      The id of the object to be deleted
     * @return T                      The deleted object
     * @throws NoSuchElementException If the object with given id is not found
     */
    public T delete(Id id) throws SQLException, RepositoryException {
        return getRepository().delete(id);
    }

    protected Integer getIntOrNull(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public void hydrate(List<T> items, HydrationType hydrationType) throws RepositoryException {
        if (hydrationType == HydrationType.FULL) {
            hydrateFull(items);
        }
    }

    protected void hydrateFull(Collection<T> items) throws RepositoryException {

    }

    protected abstract Repository<Id, T> getRepository();
}
