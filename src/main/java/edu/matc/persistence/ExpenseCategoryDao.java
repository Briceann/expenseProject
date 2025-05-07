package edu.matc.persistence;

import edu.matc.entity.ExpenseCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * The Dao provides methods to insert, retrieve by ID, and
 * retrieve all expense categories.
 * Uses Hibernate for ORM operations.
 */
public class ExpenseCategoryDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Inserts a new ExpenseCategory into the database.
     *
     * @param category the ExpenseCategory to insert
     * @return the generated category ID
     */
    public int insert(ExpenseCategory category) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(category);
        transaction.commit();
        id = category.getCategoryId(); // Assuming your ExpenseCategory entity has a field "id"
        session.close();
        return id;
    }

    /**
     * Get ExpenseCategory by Id
     *
     * @param id the category Id
     * @return the ExpenseCategory object
     */
    public ExpenseCategory getCategoryById(int id) {
        Session session = sessionFactory.openSession();
        ExpenseCategory expenseCategory = session.get(ExpenseCategory.class, id);
        session.close();
        return expenseCategory;
    }

    /**
     * Retrieves all expense categories in the database.
     *
     * @return a list of ExpenseCategory objects
     */
    public List<ExpenseCategory> getAllCategories() {
        Session session = sessionFactory.openSession();
        List<ExpenseCategory> categories = session.createQuery("from ExpenseCategory", ExpenseCategory.class).list();
        session.close();
        return categories;
    }
}
