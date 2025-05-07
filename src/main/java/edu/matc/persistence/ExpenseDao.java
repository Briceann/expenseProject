package edu.matc.persistence;

import edu.matc.entity.Expense;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Expense Dao class for managing Expense entity operations using Hibernate.
 *  Provides CRUD methods and custom queries such as:
 *  Retrieving expenses by user
 * Fetching total spent by category
 * Getting recent or filtered expenses
 *
 * @author Btaneh
 */
public class ExpenseDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Get expense by id.
     *
     * @param id Expense id.
     * @return Expense corresponding to the given id.
     */
    public Expense getById(int id) {
        Session session = sessionFactory.openSession();
        Expense expense = session.get(Expense.class, id);
        session.close();
        return expense;
    }

    /**
     * Update an expense.
     *
     * @param expense Expense to be updated.
     */
    public void update(Expense expense) {
        logger.debug("Before update: " + expense);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(expense);
        transaction.commit();
        logger.debug("Updating expense: " + expense.getExpenseId());
        session.close();
    }

    /**
     * Insert a new expense.
     *
     * @param expense Expense to be inserted.
     * @return The generated id for the new expense.
     */
    public int insert(Expense expense) {
        int expenseId = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(expense);
        transaction.commit();
        expenseId = expense.getExpenseId();
        session.close();
        return expenseId;
    }

    /**
     * Delete an expense.
     *
     * @param expense Expense to be deleted.
     */
    public void delete(Expense expense) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(expense);
        transaction.commit();
        session.close();
    }

    /**
     * Return a list of all expenses.
     *
     * @return All expenses.
     */
    public List<Expense> getAllExpenses() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Expense> query = session.getCriteriaBuilder().createQuery(Expense.class);
        Root<Expense> root = query.from(Expense.class);
        List<Expense> expenses = session.createQuery(query).getResultList();

        logger.debug("The list of expenses: " + expenses);
        session.close();

        return expenses;
    }

    /**
     * Retrieves all expenses belonging to a specific user
     *
     * @param userId the user ID
     * @return list of that user's expenses
     */
    public List<Expense> getExpensesByUserId(int userId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Expense> query = builder.createQuery(Expense.class);
        Root<Expense> root = query.from(Expense.class);

        query.select(root).where(builder.equal(root.get("user").get("userId"), userId)).orderBy(builder.desc(root.get("date")));
        List<Expense> expenses = session.createQuery(query).getResultList();
        logger.debug("Expenses for userId {}: {}", userId, expenses);
        //session.close();
        return expenses;
    }

    /**
     * Retrieves total amount spent per category for a user.
     * Includes categories with 0 spending using LEFT JOIN.
     *
     * @param userId the user ID
     * @return map of category name to total spent amount
     */
    public Map<String, Double> getAllCategoryTotalsForUser(int userId) {
        Session session = sessionFactory.openSession();

        String sql = "SELECT c.name, COALESCE(SUM(e.amount), 0) " +
                "FROM expense_categories c " +
                "LEFT JOIN expenses e ON e.category_id = c.category_id AND e.user_id = :userId " +
                "GROUP BY c.name";

        List<Object[]> results = session.createNativeQuery(sql)
                .setParameter("userId", userId)
                .list();

        Map<String, Double> totals = new LinkedHashMap<>();
        for (Object[] row : results) {
            totals.put((String) row[0], ((Number) row[1]).doubleValue());
        }

        session.close();
        return totals;
    }

    /**
     * Retrieves expenses for a user from the past X days.
     *
     * @param userId the user ID
     * @param pastDays number of days to look back
     * @return list of recent expenses
     */
    public List<Expense> getRecentExpenses(int userId, int pastDays) {
        Session session = sessionFactory.openSession();

        String sql = "SELECT * FROM expenses WHERE user_id = :userId " +
                "AND DATE(date) >= CURDATE() - INTERVAL " + pastDays +
                " DAY ORDER BY date DESC";

        List<Expense> expenses = session.createNativeQuery(sql, Expense.class)
                .setParameter("userId", userId)
                //.setParameter("days", pastDays)
                .getResultList();

        session.close();
        return expenses;
    }

    /**
     * Retrieves expenses for a user filtered by category name.
     *
     * @param userId the user ID
     * @param categoryName the category name
     * @return list of expenses in that category
     */
    public List<Expense> getExpensesByCategory(int userId, String categoryName) {
        Session session = sessionFactory.openSession();

        String sql = "SELECT e.* FROM expenses e " +
                "JOIN expense_categories c ON e.category_id = c.category_id " +
                "WHERE e.user_id = :userId AND c.name = :categoryName";

        List<Expense> expenses = session.createNativeQuery(sql, Expense.class)
                .setParameter("userId", userId)
                .setParameter("categoryName", categoryName)
                .list();

        session.close();
        return expenses;
    }

}
