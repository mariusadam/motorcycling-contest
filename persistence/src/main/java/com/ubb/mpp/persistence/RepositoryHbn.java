package com.ubb.mpp.persistence;

import com.ubb.mpp.model.HasId;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Marius Adam
 */
public abstract class RepositoryHbn<Id extends Serializable, T extends HasId<Id>> implements Repository<Id, T> {
    final protected SessionFactory sessionFactory;
    final protected Class<T> managedEntity;

    public RepositoryHbn(SessionFactory sessionFactory, Class<T> managedEntity) {
        this.sessionFactory = sessionFactory;
        this.managedEntity = managedEntity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(Id id) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Object obj = session.load(managedEntity, id);
        trans.commit();

        return obj == null ? null : (T) obj;
    }

    @Override
    public void insert(T obj) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        Serializable generatedId = session.save(obj);
        trans.commit();
    }

    @Override
    public T delete(Id id) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        T obj = findById(id);
        session.delete(obj);
        transaction.commit();
        return obj;
    }

    @Override
    public void update(T entity) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.update(entity);
        trans.commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findBy(Map<String, String> criteriaMap) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(managedEntity);
        criteriaMap.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String value) {
                criteria.add(Restrictions.eq(s, value));
            }
        });
        List<T> list = (List<T>) criteria.list();
        transaction.commit();

        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findBy(String property, Object value) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        Transaction t = session.beginTransaction();
        Criteria criteria = session.createCriteria(managedEntity);
        criteria.add(Restrictions.eq(property, value));
        List<T> list = (List<T>) criteria.list();
        t.commit();

        return list;
    }

    @Override
    public T findOneBy(String property, Object value) throws RepositoryException {
        Collection<T> result = findBy(property, value);
        if (result.isEmpty()) {
            throw new RepositoryException(String.format(
                    "Could not find an %s with \"%s\" equal to \"%s\"",
                    managedEntity.getSimpleName(),
                    property,
                    value
            ));
        }

        return result.iterator().next();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<T> list = (List<T>) session.createCriteria(managedEntity).list();
        transaction.commit();

        return list;
    }

    @Override
    public void addCollection(Collection<T> collection) throws RepositoryException {
        for(T o : collection) {
            insert(o);
        }
    }

    @Override
    public int size() throws RepositoryException {
        // TODO: replace this method with a query because it is stupid to do it like that
        return getAll().size();
    }
}
