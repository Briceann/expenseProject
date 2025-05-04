package edu.matc.persistence;

import edu.matc.entity.User;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * UserDao class
 * @author Btaneh
 */
public class UserDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Get user by ID
     */
    public User getUserById(int user_id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, user_id);
        session.close();
        return user;
    }

    /**
     * insert a new user
     * @param user User to be inserted
     */
    public int insertUser(User user) {
        int user_id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        user_id = user.getUserId();
        session.close();
        return user_id;
    }

    /**
     * update user
     * @param user  User to be updated
     */
    public void update(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    /**
     * Delete a user
     * @param user User to be deleted
     */
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<User> users = session.createSelectionQuery( query ).getResultList();
        logger.debug("List of users: " + users);
        session.close();
        return users;

    }

    public int insert(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return user.getUserId();
    }


    public List<User> getAllUsersWithExpenses() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);

            // Fetch expenses and categories
            root.fetch("expenses", JoinType.LEFT);

            query.select(root);
            users = session.createQuery(query).getResultList();

            logger.info("Retrieved " + users.size() + " users with expenses.");
        } catch (Exception e) {
            logger.error("Error fetching users with expenses", e);
        }
        return users;
    }

    /**
     * Get user by property (exact match)
     * sample usage: getByPropertyEqual("lastname", "Adams")
     */
    public List<User> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for user with " + propertyName + " = " + value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<User> users = session.createSelectionQuery( query ).getResultList();

        session.close();
        return users;
    }

}


