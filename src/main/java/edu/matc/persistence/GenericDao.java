package edu.matc.persistence;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Generic Dao
 */
public class GenericDao<T> {
    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Instantiate a new Generic dao
     *
     * @param type the entity type
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * Get entity by id.
     *
     * @param id entity id.
     * @return entity corresponding to the given id.
     */
    public T getById(int id) {
        Session session = getSession();
        T entity = session.get(type, id);
        session.close();
        return entity;
    }

    /**
     * Return a list of all entity.
     *
     * @return All entity.
     */
    public List<T> getAll() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root);
        List<T> list = session.createQuery(query).getResultList();
        logger.debug("The list of expenses: " + list);
        session.close();
        return list;
    }

    /**
     * Insert an entity.
     *
     * @param entity entity to be inserted.
     * @return The generated id for the new entity.
     */
    public int insert(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(entity);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * update an entity
     * @param entity to be updated
     */
    public void update(T entity) {
        logger.debug("Before update: " + entity);
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Delete an entity.
     *
     * @param entity entity to be deleted.
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Get entity by property
     * @param propertyName the entity name
     * @param value the entity value
     * @return entity
     */
    public List<T> getByPropertyEqual(String propertyName, Object value) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<T> results = session.createQuery(query).getResultList();
        session.close();
        return results;
    }

    /**
     * Return an open session from the SessionFactory
     *
     * @return session
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }
}
